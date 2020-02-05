create unique index IX_BFC5C070 on AssetEntryUsage (assetEntryId, containerType, containerKey[$COLUMN_LENGTH:200$], plid, ctCollectionId);
create index IX_EBDE9FB4 on AssetEntryUsage (assetEntryId, ctCollectionId);
create index IX_D010201D on AssetEntryUsage (assetEntryId, type_, ctCollectionId);
create index IX_5EA4B621 on AssetEntryUsage (containerType, containerKey[$COLUMN_LENGTH:200$], plid, ctCollectionId);
create index IX_8DA9ACD on AssetEntryUsage (plid, containerType, containerKey[$COLUMN_LENGTH:200$]);
create index IX_B5C8DC2E on AssetEntryUsage (plid, ctCollectionId);
create index IX_B90A9E57 on AssetEntryUsage (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_D54DFB2D on AssetEntryUsage (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_E21D8B19 on AssetEntryUsage (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);