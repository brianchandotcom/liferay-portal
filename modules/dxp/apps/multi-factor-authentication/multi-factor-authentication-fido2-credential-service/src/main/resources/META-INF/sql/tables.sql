create table MFAFIDO2CredentialEntry (
	mvccVersion LONG default 0 not null,
	mfaFIDO2CredentialEntryId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	userId LONG,
	credentialId VARCHAR(128) null,
	credentialType INTEGER,
	publicKeyCose VARCHAR(128) null,
	signatureCount LONG,
	failedAttempts INTEGER
);