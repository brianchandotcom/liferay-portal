create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create index IX_7E6DE59C on LVEntry (groupId);
create unique index IX_50CAD09D on LVEntry (headId);

create unique index IX_F78A58FD on LVEntryLocalization (entryId, languageId[$COLUMN_LENGTH:75$]);
create unique index IX_FC1C4C16 on LVEntryLocalization (headId);

create unique index IX_224EB3C1 on LVEntryLocalizationVersion (entryId, languageId[$COLUMN_LENGTH:75$], version);
create index IX_39980428 on LVEntryLocalizationVersion (entryId, version);
create unique index IX_EAC6D2F9 on LVEntryLocalizationVersion (lvEntryLocalizationId, version);

create unique index IX_F8303E81 on LVEntryVersion (entryId, version);
create index IX_78E84D94 on LVEntryVersion (groupId, version);

create unique index IX_71664DCE on LocalizedEntryLocalization (entryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_60161569 on VersionedEntry (groupId);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create unique index IX_51A1344E on VersionedEntryVersion (entryId, version);
create index IX_D2594361 on VersionedEntryVersion (groupId, version);