create table CommerceUserSegmentCriterion (
	commerceUserSegmentCriterionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceUserSegmentEntryId LONG,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	priority DOUBLE
);

create table CommerceUserSegmentEntry (
	commerceUserSegmentEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	key_ VARCHAR(75) null,
	active_ BOOLEAN,
	system BOOLEAN,
	priority DOUBLE
);