create index IX_85EFE02D on SavedContentEntry (classNameId, classPK, companyId);
create unique index IX_83823B11 on SavedContentEntry (classNameId, classPK, userId, companyId, ctCollectionId);
create unique index IX_51690E53 on SavedContentEntry (classNameId, classPK, userId, groupId, ctCollectionId);
create index IX_ECAA972A on SavedContentEntry (userId, groupId);
create index IX_AAA0B3AE on SavedContentEntry (uuid_[$COLUMN_LENGTH:75$]);