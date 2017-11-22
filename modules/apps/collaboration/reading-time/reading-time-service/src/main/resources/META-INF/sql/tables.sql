create table ReadingTimeEntry (
	uuid_ VARCHAR(75) null,
	readingTimeEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	readingTime LONG
);