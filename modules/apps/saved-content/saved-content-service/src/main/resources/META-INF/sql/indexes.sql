create index IX_2F9B1655 on SavedContentEntry (companyId, classNameId, classPK, ctCollectionId);
create unique index IX_4715B10F on SavedContentEntry (companyId, userId, classNameId, classPK, ctCollectionId);
create index IX_EC57682 on SavedContentEntry (groupId, ctCollectionId);
create index IX_6E4EB6BC on SavedContentEntry (groupId, userId, ctCollectionId);
create index IX_3F8E562C on SavedContentEntry (userId, classNameId, ctCollectionId);
create index IX_6031E3DE on SavedContentEntry (userId, ctCollectionId);