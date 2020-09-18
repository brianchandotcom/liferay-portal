create index IX_CFD62AAB on MFAFIDO2CredentialEntry (credentialId[$COLUMN_LENGTH:128$]);
create unique index IX_550A6065 on MFAFIDO2CredentialEntry (userId, credentialId[$COLUMN_LENGTH:128$]);