create index IX_344B5AB8 on JSONStorageEntry (classNameId, classPK, ctCollectionId);
create unique index IX_7412B525 on JSONStorageEntry (classNameId, classPK, parentJSONStorageEntryId, index_, key_[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_1D1A4852 on JSONStorageEntry (companyId, classNameId, index_, type_, valueLong, ctCollectionId);
create index IX_52780E25 on JSONStorageEntry (companyId, classNameId, key_[$COLUMN_LENGTH:255$], type_, valueLong, ctCollectionId);