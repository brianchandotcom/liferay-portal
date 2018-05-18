create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create index IX_7E6DE59C on LVEntry (groupId);
create unique index IX_50CAD09D on LVEntry (headId);

create unique index IX_FC1C4C16 on LVEntryLocalization (headId);
create unique index IX_5233ABD3 on LVEntryLocalization (lvEntryId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_B05C042B on LVEntryLocalizationVersion (lvEntryId, languageId[$COLUMN_LENGTH:75$], version);
create index IX_D41B2392 on LVEntryLocalizationVersion (lvEntryId, version);
create unique index IX_EAC6D2F9 on LVEntryLocalizationVersion (lvEntryLocalizationId, version);

create index IX_78E84D94 on LVEntryVersion (groupId, version);
create unique index IX_4D8E2BAB on LVEntryVersion (lvEntryId, version);

create unique index IX_2E833843 on LocalizedEntryLocalization (localizedEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_60161569 on VersionedEntry (groupId);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);