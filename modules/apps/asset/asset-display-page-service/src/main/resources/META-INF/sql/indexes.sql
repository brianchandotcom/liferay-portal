create index IX_31FA120C on AssetDisplayPageEntry (classNameId, classPK);
create unique index IX_89672180 on AssetDisplayPageEntry (groupId, classNameId, classPK, companyId);
create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId);
create index IX_1DA6952B on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_27FEC39B on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);