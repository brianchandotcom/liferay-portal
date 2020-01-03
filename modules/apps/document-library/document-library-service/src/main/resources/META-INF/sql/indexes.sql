create unique index IX_DB371688 on DLFileVersionPreview (fileEntryId, fileVersionId, companyId);
create unique index IX_D272077E on DLFileVersionPreview (fileEntryId, fileVersionId, previewStatus, companyId);
create index IX_E43957CD on DLFileVersionPreview (fileVersionId);