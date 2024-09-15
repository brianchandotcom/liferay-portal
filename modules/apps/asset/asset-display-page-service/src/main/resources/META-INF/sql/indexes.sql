create unique index IX_F3AB130A on AssetDisplayPageEntry (groupId, classNameId, ctCollectionId, classPK);
create unique index IX_9920AB1F on AssetDisplayPageEntry (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId);
create index IX_DEA3F2DD on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$]);