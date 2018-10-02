create unique index IX_34CEA368 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$]);
create index IX_4FE08A35 on AssetListEntry (groupId, type_);
create index IX_DD7DDFBE on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AFD73DC0 on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_79D39729 on AssetListEntryAssetEntryRel (assetListEntryId, position);
create index IX_99DDCF6D on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3D7D4D2F on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId);