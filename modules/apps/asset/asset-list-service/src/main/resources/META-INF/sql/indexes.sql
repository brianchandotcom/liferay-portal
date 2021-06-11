create index IX_6BEBFA56 on AssetListEntry (groupId, assetEntrySubtype[$COLUMN_LENGTH:255$], assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_D604A2E on AssetListEntry (groupId, assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_366FAE09 on AssetListEntry (groupId, assetListEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_E5ED21FE on AssetListEntry (groupId, ctCollectionId);
create index IX_FD621D8E on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], assetEntrySubtype[$COLUMN_LENGTH:255$], assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_CBD041F6 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], assetEntryType[$COLUMN_LENGTH:255$], ctCollectionId);
create unique index IX_5B95A9C6 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_6A225693 on AssetListEntry (groupId, type_, ctCollectionId);
create index IX_AF5D7A1C on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_D48EC888 on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_B08E941E on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_AB0636AA on AssetListEntryAssetEntryRel (assetListEntryId, ctCollectionId);
create index IX_3F8820EF on AssetListEntryAssetEntryRel (assetListEntryId, segmentsEntryId, ctCollectionId);
create unique index IX_FAAE938C on AssetListEntryAssetEntryRel (assetListEntryId, segmentsEntryId, position, ctCollectionId);
create index IX_881E2BCB on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_FC6C4E39 on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_1FDDE58D on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_8BB55022 on AssetListEntrySegmentsEntryRel (assetListEntryId, ctCollectionId);
create unique index IX_56302677 on AssetListEntrySegmentsEntryRel (assetListEntryId, segmentsEntryId, ctCollectionId);
create index IX_865F28AA on AssetListEntrySegmentsEntryRel (segmentsEntryId, ctCollectionId);
create index IX_68CD4543 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_AB2E6FC1 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_29F4FD05 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_76643456 on AssetListEntryUsage (assetListEntryId, classNameId, ctCollectionId);
create index IX_6E1C4974 on AssetListEntryUsage (assetListEntryId, ctCollectionId);
create index IX_669BC47F on AssetListEntryUsage (containerKey[$COLUMN_LENGTH:255$], containerType, plid, ctCollectionId);
create unique index IX_624112AF on AssetListEntryUsage (groupId, classNameId, containerKey[$COLUMN_LENGTH:255$], containerType, key_[$COLUMN_LENGTH:255$], plid, ctCollectionId);
create index IX_AF0F2C99 on AssetListEntryUsage (groupId, classNameId, key_[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_27D73798 on AssetListEntryUsage (groupId, classNameId, key_[$COLUMN_LENGTH:255$], type_, ctCollectionId);
create index IX_9722696C on AssetListEntryUsage (plid, ctCollectionId);
create index IX_4B343E95 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_1F2615AF on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_AE0E22D7 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);