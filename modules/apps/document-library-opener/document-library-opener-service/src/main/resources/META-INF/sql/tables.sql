create table DLOpenerFileEntryReference (
	dlOpenerFileEntryReferenceId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	referenceKey VARCHAR(75) null,
	referenceType VARCHAR(75) null,
	fileEntryId LONG,
	type_ INTEGER,
	primary key (dlOpenerFileEntryReferenceId, companyId)
);