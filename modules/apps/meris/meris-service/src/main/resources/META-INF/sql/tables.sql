create table MerisSegment (
	merisSegmentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	key_ VARCHAR(75) null,
	active_ BOOLEAN,
	type_ VARCHAR(75) null,
	criteria VARCHAR(75) null
);

create table MerisSegmentRel (
	merisSegmentRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	merisSegmentId LONG,
	classNameId LONG,
	classPK LONG
);