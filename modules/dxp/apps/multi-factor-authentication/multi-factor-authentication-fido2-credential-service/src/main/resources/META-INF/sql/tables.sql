create table MFAFIDO2CredentialEntry (
	mvccVersion LONG default 0 not null,
	mfaFIDO2CredentialEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	credentialKey VARCHAR(128) null,
	credentialType INTEGER,
	failedAttempts INTEGER,
	publicKeyCOSE VARCHAR(128) null,
	signatureCount LONG
);