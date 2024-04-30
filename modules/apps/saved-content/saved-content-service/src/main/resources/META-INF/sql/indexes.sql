create index IX_85EFE02D on SavedContentEntry (classNameId, classPK, companyId);
create index IX_5F4B6779 on SavedContentEntry (groupId, classNameId, classPK);
create unique index IX_CFA82491 on SavedContentEntry (groupId, userId, classNameId, classPK, ctCollectionId);
create unique index IX_8168C76E on SavedContentEntry (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_55E08A15 on SavedContentEntry (userId, classNameId, classPK, ctCollectionId, companyId);
create index IX_AAA0B3AE on SavedContentEntry (uuid_[$COLUMN_LENGTH:75$]);