create index IX_957FE3BD on StyleBookEntry (groupId, defaultStyleBookEntry);
create index IX_F379E6EB on StyleBookEntry (groupId, name[$COLUMN_LENGTH:75$]);
create unique index IX_9A76A32B on StyleBookEntry (groupId, styleBookEntryKey[$COLUMN_LENGTH:75$]);