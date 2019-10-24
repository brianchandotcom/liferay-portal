create table CTSContent (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	ctsContentId LONG not null,
	companyId LONG,
	repositoryId LONG,
	path_ VARCHAR(75) null,
	version VARCHAR(75) null,
	data_ BLOB,
	size_ LONG,
	storeType VARCHAR(75) null,
	primary key (ctsContentId, ctCollectionId)
);