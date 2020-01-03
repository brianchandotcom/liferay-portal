create table ReadingTimeEntry (
	uuid_ VARCHAR(75) null,
	readingTimeEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	readingTime LONG,
	primary key (readingTimeEntryId, companyId)
);