create table CommerceUserSegmentEntry (
	commerceUserSegmentEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	priority DOUBLE,
	active_ BOOLEAN
);