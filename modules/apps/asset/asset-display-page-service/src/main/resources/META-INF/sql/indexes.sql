create index IX_31FA120C on AssetDisplayPageEntry (classNameId, classPK);
create unique index IX_68525006 on AssetDisplayPageEntry (groupId, classNameId, classPK, ctCollectionId);
create index IX_185EE7F1 on AssetDisplayPageEntry (groupId, ctCollectionId);
create index IX_54CF971 on AssetDisplayPageEntry (layoutPageTemplateEntryId, ctCollectionId);
create index IX_11CFB589 on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_48F6F3B on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_EF42C6CB on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);