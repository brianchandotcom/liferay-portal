create table DLFileEntryPreview (
	fileEntryPreviewId LONG not null primary key,
	groupId LONG,
	fileEntryId LONG,
	fileVersionId LONG,
	previewType INTEGER
);