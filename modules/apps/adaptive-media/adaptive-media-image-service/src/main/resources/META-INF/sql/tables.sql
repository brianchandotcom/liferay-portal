create table AMImageEntry (
	uuid_ VARCHAR(75) null,
	amImageEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	createDate DATE null,
	configurationUuid VARCHAR(75) null,
	fileVersionId LONG,
	mimeType VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	size_ LONG,
	primary key (amImageEntryId, companyId)
);