create index IX_FBDFFFF8 on DepotEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_418C2F6E on DepotEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_E0E42D64 on DepotEntryGroupRel (depotEntryId, toGroupId, companyId);
create index IX_DB75E9F1 on DepotEntryGroupRel (toGroupId);