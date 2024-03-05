create index IX_5B76D798 on DepotAppCustomization (depotEntryId, enabled);
create unique index IX_2CE1592A on DepotAppCustomization (depotEntryId, portletId[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_E3EB2C84 on DepotEntry (groupId, ctCollectionId);

create unique index IX_F1E19412 on DepotEntryGroupRel (ctCollectionId, uuid_[$COLUMN_LENGTH:75$], groupId);
create index IX_146497CB on DepotEntryGroupRel (depotEntryId);
create index IX_7CA33F81 on DepotEntryGroupRel (toGroupId, ddmStructuresAvailable);
create unique index IX_1DD0EA9C on DepotEntryGroupRel (toGroupId, depotEntryId, ctCollectionId);
create index IX_BA106967 on DepotEntryGroupRel (toGroupId, searchable);