create index IX_FBDFFFF8 on DepotEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8AD8247A on DepotEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_65D34444 on DepotEntryGroupRel (depotEntryId, toGroupId);
create index IX_DB75E9F1 on DepotEntryGroupRel (toGroupId);