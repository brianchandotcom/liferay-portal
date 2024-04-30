create unique index IX_64F7EFA0 on CPDefinitionGroupedEntry (CPDefinitionId, entryCProductId);
create index IX_8B75194F on CPDefinitionGroupedEntry (entryCProductId);
create unique index IX_E30475B0 on CPDefinitionGroupedEntry (uuid_[$COLUMN_LENGTH:75$], groupId);