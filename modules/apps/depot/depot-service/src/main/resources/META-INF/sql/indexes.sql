create index IX_5B76D798 on DepotAppCustomization (depotEntryId, enabled);
create unique index IX_DA8D9ACC on DepotAppCustomization (depotEntryId, portletId[$COLUMN_LENGTH:75$]);

create unique index IX_884D6226 on DepotEntry (groupId);

create index IX_146497CB on DepotEntryGroupRel (depotEntryId);
create index IX_7CA33F81 on DepotEntryGroupRel (toGroupId, ddmStructuresAvailable);
create unique index IX_EDE2503E on DepotEntryGroupRel (toGroupId, depotEntryId);
create index IX_BA106967 on DepotEntryGroupRel (toGroupId, searchable);
create unique index IX_D25F75B4 on DepotEntryGroupRel (uuid_[$COLUMN_LENGTH:75$], groupId);