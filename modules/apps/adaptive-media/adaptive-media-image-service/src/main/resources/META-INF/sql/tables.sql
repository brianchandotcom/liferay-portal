create table AMImageEntry (
	uuid_ VARCHAR(75) null,
	amImageEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	configurationUuid VARCHAR(75) null,
	fileVersionId LONG,
	mimeType VARCHAR(75) null,
	height INTEGER,
	width INTEGER,
	size_ LONG
);