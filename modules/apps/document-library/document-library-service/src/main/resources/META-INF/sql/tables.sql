create table DLFileVersionPreview (
	dlFileVersionPreviewId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	fileEntryId LONG,
	fileVersionId LONG,
	previewStatus INTEGER
);