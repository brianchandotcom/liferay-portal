create table DLFileVersionPreview (
	dlFileVersionPreviewId LONG not null primary key,
	groupId LONG,
	fileEntryId LONG,
	fileVersionId LONG,
	previewStatus INTEGER
);

create unique index IX_9B6A9A0 on DLFileVersionPreview (fileEntryId, fileVersionId);
create index IX_E43957CD on DLFileVersionPreview (fileVersionId);

COMMIT_TRANSACTION;