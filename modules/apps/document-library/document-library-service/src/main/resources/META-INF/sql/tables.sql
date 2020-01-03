create table DLFileVersionPreview (
	dlFileVersionPreviewId LONG not null,
	groupId LONG,
	companyId LONG not null,
	fileEntryId LONG,
	fileVersionId LONG,
	previewStatus INTEGER,
	primary key (dlFileVersionPreviewId, companyId)
);