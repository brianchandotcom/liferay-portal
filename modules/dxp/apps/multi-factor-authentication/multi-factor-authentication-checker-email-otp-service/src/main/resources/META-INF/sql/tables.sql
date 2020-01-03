create table MFAEmailOTPEntry (
	mvccVersion LONG default 0 not null,
	mfaEmailOTPEntryId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	failedAttempts INTEGER,
	lastFailDate DATE null,
	lastFailIP VARCHAR(75) null,
	lastSuccessDate DATE null,
	lastSuccessIP VARCHAR(75) null,
	primary key (mfaEmailOTPEntryId, companyId)
);