create unique index IX_34CEA368 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$]);
create index IX_4FE08A35 on AssetListEntry (groupId, type_);

create unique index IX_79D39729 on AssetListEntryAssetEntryRel (assetListEntryId, position);