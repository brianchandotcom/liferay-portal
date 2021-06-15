create index IX_BBAC7412 on JSONStoreEntry (classNameId, classPK, ctCollectionId);
create unique index IX_D0DA9959 on JSONStoreEntry (classNameId, classPK, parentJSONStoreEntryId, index_, key_[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_C6236AC on JSONStoreEntry (companyId, classNameId, index_, type_, valueLong, ctCollectionId);
create index IX_83BBCAFF on JSONStoreEntry (companyId, classNameId, key_[$COLUMN_LENGTH:255$], type_, valueLong, ctCollectionId);