create unique index IX_31FA120C on AssetDisplayPageEntry (classNameId, classPK);
create index IX_CD779793 on AssetDisplayPageEntry (groupId);
create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId);
create index IX_1DA6952B on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DB986A6D on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId);