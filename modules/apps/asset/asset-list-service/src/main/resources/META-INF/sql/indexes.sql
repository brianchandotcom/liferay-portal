create unique index IX_5891CD9D on AssetListEntry (groupId, assetListEntryKey[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7862AFC0 on AssetListEntry (groupId, title[$COLUMN_LENGTH:75$], companyId);
create index IX_4FE08A35 on AssetListEntry (groupId, type_);
create index IX_DD7DDFBE on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_CAE6AE68 on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_EDA4423A on AssetListEntryAssetEntryRel (assetListEntryId, segmentsEntryId, position, companyId);
create index IX_99DDCF6D on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1837F399 on AssetListEntryAssetEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_DF87486F on AssetListEntrySegmentsEntryRel (assetListEntryId, segmentsEntryId, companyId);
create index IX_1C9A6A4C on AssetListEntrySegmentsEntryRel (segmentsEntryId);
create index IX_541ED8E5 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6E7E1121 on AssetListEntrySegmentsEntryRel (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_6CA44DF8 on AssetListEntryUsage (assetListEntryId, classNameId);
create unique index IX_AC8086FB on AssetListEntryUsage (classNameId, classPK, portletId[$COLUMN_LENGTH:200$], companyId);
create index IX_4976B637 on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3135100F on AssetListEntryUsage (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);