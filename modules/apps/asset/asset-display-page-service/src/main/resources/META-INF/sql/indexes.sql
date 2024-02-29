create unique index IX_73E15F86 on AssetDisplayPageEntry (groupId, ctCollectionId, classNameId, classPK);
create unique index IX_1F3EA0A3 on AssetDisplayPageEntry (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId);