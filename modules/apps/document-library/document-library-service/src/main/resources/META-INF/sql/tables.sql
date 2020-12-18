create table DLFileVersionPreview (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	dlFileVersionPreviewId LONG not null,
	groupId LONG,
	companyId LONG,
	fileEntryId LONG,
	fileVersionId LONG,
	previewStatus INTEGER,
	primary key (dlFileVersionPreviewId, ctCollectionId)
);

create table DLStorageQuota (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	dlStorageQuotaId LONG not null,
	companyId LONG,
	storageSize LONG,
	primary key (dlStorageQuotaId, ctCollectionId)
);