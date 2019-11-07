create table MFAEmailOTPEntry (
	mvccVersion LONG default 0 not null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	failedAttempts INTEGER,
	lastSuccessDate DATE null,
	lastSuccessIP VARCHAR(75) null,
	lastFailDate DATE null,
	lastFailIP VARCHAR(75) null
);