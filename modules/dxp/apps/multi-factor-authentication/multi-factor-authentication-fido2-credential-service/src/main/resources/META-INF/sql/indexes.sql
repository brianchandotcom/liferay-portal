create index IX_A95911A1 on MFAFIDO2CredentialEntry (credentialKeyHash);
create unique index IX_F2E36027 on MFAFIDO2CredentialEntry (userId, credentialKeyHash);