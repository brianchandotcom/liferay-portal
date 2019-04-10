create table SegmentsEntry (
	uuid_ VARCHAR(75) null,
	segmentsEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	segmentsEntryKey VARCHAR(75) null,
	name STRING null,
	description STRING null,
	active_ BOOLEAN,
	criteria TEXT null,
	source VARCHAR(75) null,
	type_ VARCHAR(75) null,
	lastPublishDate DATE null
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

create table SegmentsExperience (
	uuid_ VARCHAR(75) null,
	segmentsExperienceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	segmentsEntryId LONG,
	classNameId LONG,
	classPK LONG,
	name STRING null,
	priority INTEGER,
	active_ BOOLEAN,
	lastPublishDate DATE null
);