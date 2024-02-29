create index IX_85EFE02D on SavedContentEntry (classNameId, classPK, companyId);
create unique index IX_80CE4E8B on SavedContentEntry (classNameId, classPK, ctCollectionId, groupId, userId);
create unique index IX_503EA195 on SavedContentEntry (classNameId, classPK, ctCollectionId, userId, companyId);
create unique index IX_73751F6E on SavedContentEntry (ctCollectionId, groupId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_AAA0B3AE on SavedContentEntry (uuid_[$COLUMN_LENGTH:75$]);