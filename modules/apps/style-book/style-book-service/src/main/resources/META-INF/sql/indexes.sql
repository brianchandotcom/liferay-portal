create index IX_C95383D1 on StyleBookEntry (groupId, defaultStyleBookEntry, head);
create index IX_E96FB900 on StyleBookEntry (groupId, head);
create index IX_77DDEC7F on StyleBookEntry (groupId, name[$COLUMN_LENGTH:75$], head);
create unique index IX_3EEC78BF on StyleBookEntry (groupId, styleBookEntryKey[$COLUMN_LENGTH:75$], head);
create unique index IX_B5BABC0D on StyleBookEntry (headId);

create index IX_3CA4E523 on StyleBookEntryVersion (groupId, defaultStyleBookEntry, version);
create index IX_7B3A245 on StyleBookEntryVersion (groupId, name[$COLUMN_LENGTH:75$], version);
create unique index IX_5F837D75 on StyleBookEntryVersion (groupId, styleBookEntryKey[$COLUMN_LENGTH:75$], version);
create index IX_9A3EB024 on StyleBookEntryVersion (groupId, version);
create unique index IX_21DCB95B on StyleBookEntryVersion (styleBookEntryId, version);