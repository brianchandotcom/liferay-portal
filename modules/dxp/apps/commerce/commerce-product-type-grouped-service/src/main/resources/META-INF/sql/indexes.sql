create unique index IX_85D6EC94 on CPDefinitionGroupedEntry (CPDefinitionId, entryCPDefinitionId);
create index IX_FA3CDBAE on CPDefinitionGroupedEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E30475B0 on CPDefinitionGroupedEntry (uuid_[$COLUMN_LENGTH:75$], groupId);