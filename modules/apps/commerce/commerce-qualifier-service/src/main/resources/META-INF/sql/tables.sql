create table CommerceQualifierEntry (
	mvccVersion LONG default 0 not null,
	commerceQualifierEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	sourceClassNameId LONG,
	sourceClassPK LONG,
	sourceMetadataKey VARCHAR(75) null,
	targetClassNameId LONG,
	targetClassPK LONG,
	targetMetadataKey VARCHAR(75) null
);