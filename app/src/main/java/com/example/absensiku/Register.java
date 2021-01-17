package com.example.absensiku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText edt_name, edt_email, edt_password;
    Button btnRegis;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edt_name = findViewById(R.id.name);
        edt_email = findViewById(R.id.email);
        edt_password = findViewById(R.id.password);
        btnRegis = findViewById(R.id.buttonRegist);
        progressBar = findViewById(R.id.progress_bar);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(Register.this, MainActivity.class));
            finish();
        }

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        String getName = edt_name.getText().toString();
        String getEmail = edt_email.getText().toString();
        String getPwd = edt_password.getText().toString();

        if(TextUtils.isEmpty(getName)){
            edt_name.setError("Name is required");
            return;
        }
        if(TextUtils.isEmpty(getEmail)){
            edt_email.setError("Email is required");
            return;
        }
        if(TextUtils.isEmpty(getPwd)){
            edt_password.setError("Password is required");
        }
        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(getEmail,getPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Berhasil Registrasi", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, MainActivity.class));
                    progressBar.setVisibility(View.GONE);
                } else{
                    Toast.makeText(Register.this, "Eror "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}