create table MFAFIDO2CredentialEntry (
	mvccVersion LONG default 0 not null,
	mfaFIDO2CredentialEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	credentialId VARCHAR(128) null,
	credentialType INTEGER,
	publicKeyCose VARCHAR(128) null,
	signatureCount LONG,
	failedAttempts INTEGER
);