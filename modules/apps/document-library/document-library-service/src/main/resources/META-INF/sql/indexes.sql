create unique index IX_18641746 on DLFileEntryPreview (fileEntryId, fileVersionId);
create index IX_E04CB467 on DLFileEntryPreview (fileVersionId);

create unique index IX_FBCCD118 on FileVersionPreview (fileEntryId, fileVersionId);
create index IX_28E06D55 on FileVersionPreview (fileVersionId);