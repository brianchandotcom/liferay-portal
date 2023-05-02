create table AMImageEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	amImageEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	configurationUuid VARCHAR(75) null,
	fileVersionId LONG,
	mimeType VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	size_ LONG,
	primary key (amImageEntryId, ctCollectionId)
);