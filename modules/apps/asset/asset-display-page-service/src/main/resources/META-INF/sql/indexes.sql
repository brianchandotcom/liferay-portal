create unique index IX_73E15F86 on AssetDisplayPageEntry (groupId, ctCollectionId, classNameId, classPK);
create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId);
create unique index IX_EF42C6CB on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);