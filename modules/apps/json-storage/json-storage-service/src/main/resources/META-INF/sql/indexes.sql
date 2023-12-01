create index IX_8FDDE8E8 on JSONStorageEntry (classNameId, companyId, index_, type_, valueLong);
create index IX_140EE1BB on JSONStorageEntry (classNameId, companyId, key_[$COLUMN_LENGTH:255$], type_, valueLong);
create unique index IX_EE36D61B on JSONStorageEntry (classNameId, index_, key_[$COLUMN_LENGTH:255$], classPK, parentJSONStorageEntryId, ctCollectionId);