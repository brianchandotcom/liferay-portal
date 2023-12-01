create index IX_D8D58598 on AssetListEntry (groupId, assetEntryType[$COLUMN_LENGTH:255$], assetEntrySubtype[$COLUMN_LENGTH:255$]);
create unique index IX_D3D0EE8D on AssetListEntry (groupId, ctCollectionId, assetListEntryKey[$COLUMN_LENGTH:75$]);
create index IX_40A918D0 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], assetEntryType[$COLUMN_LENGTH:255$], assetEntrySubtype[$COLUMN_LENGTH:255$]);
create unique index IX_5B95A9C6 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_4FE08A35 on AssetListEntry (groupId, type_);
create index IX_5B11862A on AssetListEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_FAAE938C on AssetListEntryAssetEntryRel (assetListEntryId, segmentsEntryId, position, ctCollectionId);
create index IX_EA6A8DDB on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_BD6BD27D on AssetListEntrySegmentsEntryRel (segmentsEntryId, assetListEntryId, ctCollectionId);
create index IX_770BF63 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$]);

create index IX_E1D6CA09 on AssetListEntryUsage (classNameId, key_[$COLUMN_LENGTH:255$], companyId);
create index IX_6B500AA on AssetListEntryUsage (classNameId, key_[$COLUMN_LENGTH:255$], groupId, type_);
create unique index IX_3EF446DB on AssetListEntryUsage (classNameId, key_[$COLUMN_LENGTH:255$], plid, groupId, containerType, containerKey[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_BBE5024F on AssetListEntryUsage (plid, containerType, containerKey[$COLUMN_LENGTH:255$]);
create index IX_561E0151 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$]);