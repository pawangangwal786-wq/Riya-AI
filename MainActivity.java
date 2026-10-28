package com.riya.ai;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.*;

public class MainActivity extends Activity {
    LinearLayout messages;
    EditText input;
    ScrollView scroll;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(255, 240, 246));
        getWindow().setNavigationBarColor(Color.rgb(255, 240, 246));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(255, 247, 250));

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(18), dp(14), dp(18), dp(14));
        header.setBackgroundColor(Color.rgb(255, 226, 237));
        TextView avatar = new TextView(this);
        avatar.setText("♡"); avatar.setTextSize(30); avatar.setTextColor(Color.rgb(190, 45, 105));
        header.addView(avatar);
        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL); titleBox.setPadding(dp(12), 0, 0, 0);
        TextView title = new TextView(this);
        title.setText("Riya AI"); title.setTextSize(21); title.setTypeface(null, Typeface.BOLD); title.setTextColor(Color.rgb(85, 35, 60));
        TextView subtitle = new TextView(this);
        subtitle.setText("Your virtual companion · Demo mode"); subtitle.setTextSize(12); subtitle.setTextColor(Color.rgb(120, 75, 95));
        titleBox.addView(title); titleBox.addView(subtitle); header.addView(titleBox); root.addView(header);

        scroll = new ScrollView(this);
        messages = new LinearLayout(this); messages.setOrientation(LinearLayout.VERTICAL);
        messages.setPadding(dp(12), dp(14), dp(12), dp(14)); scroll.addView(messages);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout composer = new LinearLayout(this);
        composer.setGravity(Gravity.CENTER_VERTICAL); composer.setPadding(dp(10), dp(8), dp(10), dp(8)); composer.setBackgroundColor(Color.WHITE);
        input = new EditText(this); input.setSingleLine(false); input.setMaxLines(3); input.setHint("Riya se baat karo..."); input.setTextSize(16);
        composer.addView(input, new LinearLayout.LayoutParams(0, -2, 1));
        Button send = new Button(this); send.setText("Send"); send.setTextColor(Color.WHITE);
        send.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(205, 65, 125)));
        composer.addView(send); root.addView(composer); setContentView(root);

        addMessage("Riya", "Hii ❤️ Main Riya hoon! Abhi main demo mode mein hoon. Tumhara din kaisa raha?");
        send.setOnClickListener(v -> sendMessage());
        input.setOnEditorActionListener((v, actionId, event) -> { sendMessage(); return true; });
    }

    void sendMessage() {
        String text = input.getText().toString().trim();
        if (text.isEmpty()) return;
        input.setText(""); addMessage("You", text);
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(input.getWindowToken(), 0);
        addMessage("Riya", demoReply(text));
    }

    String demoReply(String text) {
        String t = text.toLowerCase();
        if (t.contains("sad") || t.contains("dukhi") || t.contains("pareshan")) return "Aww 🥺 Kya hua? Agar tum comfortable ho, mujhe batao. Main sun rahi hoon ❤️";
        if (t.contains("love") || t.contains("pyaar") || t.contains("miss")) return "Awww, kitni sweet baat hai ❤️ Batao, aaj tumhare dil mein kya chal raha hai?";
        if (t.contains("gussa") || t.contains("angry")) return "Achha ji, pehle araam se baat karte hain 😌 Kya hua, mujhe bataoge?";
        if (t.contains("hello") || t.contains("hi") || t.contains("hii")) return "Hii 😊 Kaise ho? Aaj kya baat karein?";
        if (t.contains("joke") || t.contains("funny")) return "Ek cute si smile toh banti hai 😄 Tumhari baari—mujhe bhi ek joke sunao!";
        return "Hmm ❤️ Main tumhari baat sun rahi hoon. Thoda aur batao na, tum kya feel kar rahe ho?";
    }

    void addMessage(String who, String text) {
        TextView bubble = new TextView(this);
        bubble.setText((who.equals("You") ? "You  ·  " : "Riya  ·  ") + text);
        bubble.setTextSize(16); bubble.setTextColor(Color.rgb(65, 40, 52)); bubble.setPadding(dp(14), dp(11), dp(14), dp(11));
        bubble.setBackground(roundBg(who.equals("You") ? Color.rgb(255, 224, 237) : Color.WHITE));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2); lp.setMargins(0, 0, 0, dp(10));
        messages.addView(bubble, lp); scroll.post(() -> scroll.fullScroll(View.FOCUS_DOWN));
    }

    android.graphics.drawable.Drawable roundBg(int color) {
        android.graphics.drawable.GradientDrawable d = new android.graphics.drawable.GradientDrawable();
        d.setColor(color); d.setCornerRadius(dp(18)); return d;
    }
    int dp(int n) { return (int)(n * getResources().getDisplayMetrics().density + 0.5f); }
}
