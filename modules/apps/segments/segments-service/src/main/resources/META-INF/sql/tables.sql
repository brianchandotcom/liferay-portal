create table SegmentsEntry (
	segmentsEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	active_ BOOLEAN,
	criteria TEXT null,
	key_ VARCHAR(75) null,
	source VARCHAR(75) null,
	type_ VARCHAR(75) null
);

create table SegmentsEntryRel (
	segmentsEntryRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	segmentsEntryId LONG,
	classNameId LONG,
	classPK LONG
);