create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create unique index IX_8F9FD921 on LVEntry (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$]);
create unique index IX_50CAD09D on LVEntry (headId);
create unique index IX_8C78F044 on LVEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_FC1C4C16 on LVEntryLocalization (headId);
create unique index IX_5233ABD3 on LVEntryLocalization (lvEntryId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_B05C042B on LVEntryLocalizationVersion (lvEntryId, languageId[$COLUMN_LENGTH:75$], version);
create index IX_D41B2392 on LVEntryLocalizationVersion (lvEntryId, version);
create unique index IX_EAC6D2F9 on LVEntryLocalizationVersion (lvEntryLocalizationId, version);

create unique index IX_D4DF2FAF on LVEntryVersion (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$], version);
create index IX_78E84D94 on LVEntryVersion (groupId, version);
create unique index IX_4D8E2BAB on LVEntryVersion (lvEntryId, version);
create unique index IX_E9BD379C on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version);
create index IX_FA76694A on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], version);

create unique index IX_2E833843 on LocalizedEntryLocalization (localizedEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_60161569 on VersionedEntry (groupId);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);