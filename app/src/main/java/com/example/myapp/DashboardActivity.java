package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views with null checks
        Button btnLogout = findViewById(R.id.btnLogout);
        tvWelcome = findViewById(R.id.tvWelcome);

        // Safe user display
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String welcomeMessage = "Welcome, " + (currentUser.getEmail() != null ?
                    currentUser.getEmail() : "User");
            tvWelcome.setText(welcomeMessage);
        } else {
            tvWelcome.setText("Welcome, Guest");
        }

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(DashboardActivity.this, "Logged out successfully",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finishAffinity(); // Clears entire back stack
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}