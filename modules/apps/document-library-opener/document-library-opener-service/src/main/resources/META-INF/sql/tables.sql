create table DLOpenerFileEntryReference (
	dlOpenerFileEntryReferenceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	referenceKey VARCHAR(75) null,
	referenceType VARCHAR(75) null,
	fileEntryId LONG,
	type_ INTEGER
);