create table ReadingTimeEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	readingTimeEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	readingTime LONG,
	primary key (readingTimeEntryId, ctCollectionId)
);