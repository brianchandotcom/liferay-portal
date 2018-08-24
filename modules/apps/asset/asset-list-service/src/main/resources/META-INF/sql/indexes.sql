create index IX_4FE08A35 on AssetListEntry (groupId, type_);
create index IX_DD7DDFBE on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AFD73DC0 on AssetListEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_61D5D61D on AssetListEntryLocalization (assetListEntryId, languageId[$COLUMN_LENGTH:75$]);
create index IX_C8393BE1 on AssetListEntryLocalization (groupId, title[$COLUMN_LENGTH:75$]);