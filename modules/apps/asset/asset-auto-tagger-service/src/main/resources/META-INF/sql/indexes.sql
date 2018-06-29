create unique index IX_C2ED1189 on AssetAutoTaggerEntry (assetEntryId, assetTagId);
create index IX_357626B3 on AssetAutoTaggerEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9FAEBDF5 on AssetAutoTaggerEntry (uuid_[$COLUMN_LENGTH:75$], groupId);