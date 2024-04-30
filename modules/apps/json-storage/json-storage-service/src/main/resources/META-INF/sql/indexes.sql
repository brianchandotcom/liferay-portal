create unique index IX_28660EE5 on JSONStorageEntry (classNameId, classPK, index_, key_[$COLUMN_LENGTH:255$], parentJSONStorageEntryId, ctCollectionId);
create index IX_8FDDE8E8 on JSONStorageEntry (classNameId, companyId, index_, type_, valueLong);
create index IX_140EE1BB on JSONStorageEntry (classNameId, companyId, key_[$COLUMN_LENGTH:255$], type_, valueLong);