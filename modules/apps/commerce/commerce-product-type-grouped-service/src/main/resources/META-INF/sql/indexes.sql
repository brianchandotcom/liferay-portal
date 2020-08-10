create unique index IX_64F7EFA0 on CPDefinitionGroupedEntry (CPDefinitionId, entryCProductId);
create index IX_FA3CDBAE on CPDefinitionGroupedEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E30475B0 on CPDefinitionGroupedEntry (uuid_[$COLUMN_LENGTH:75$], groupId);