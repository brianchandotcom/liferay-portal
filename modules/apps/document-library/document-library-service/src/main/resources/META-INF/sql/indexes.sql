create index IX_D5ED40C5 on DLFileVersionPreview (fileEntryId, ctCollectionId);
create unique index IX_DA3FFE on DLFileVersionPreview (fileEntryId, fileVersionId, ctCollectionId);
create unique index IX_ACBD78C8 on DLFileVersionPreview (fileEntryId, fileVersionId, previewStatus, ctCollectionId);
create index IX_3A1CF42B on DLFileVersionPreview (fileVersionId, ctCollectionId);