create index IX_838D8DFC on BigDecimalEntries_LVEntries (companyId);
create index IX_67100507 on BigDecimalEntries_LVEntries (lvEntryId);

create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create unique index IX_24660854 on EagerBlobEntity (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C28A6270 on LVEntry (groupId, head);
create unique index IX_5013A2D3 on LVEntry (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$], head, companyId);
create unique index IX_18BF9F6B on LVEntry (headId, companyId);
create index IX_EAE3A996 on LVEntry (uuid_[$COLUMN_LENGTH:75$], companyId, head);
create unique index IX_7B9D5610 on LVEntry (uuid_[$COLUMN_LENGTH:75$], groupId, head, companyId);
create index IX_5355DC7A on LVEntry (uuid_[$COLUMN_LENGTH:75$], head);

create unique index IX_DE3A6E52 on LVEntryLocalization (headId, companyId);
create unique index IX_3E48C675 on LVEntryLocalization (lvEntryId, languageId[$COLUMN_LENGTH:75$], companyId);

create unique index IX_AD0A471D on LVEntryLocalizationVersion (lvEntryId, languageId[$COLUMN_LENGTH:75$], version, companyId);
create index IX_D41B2392 on LVEntryLocalizationVersion (lvEntryId, version);
create unique index IX_D065CB8F on LVEntryLocalizationVersion (lvEntryLocalizationId, version, companyId);

create unique index IX_77078119 on LVEntryVersion (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$], version, companyId);
create index IX_78E84D94 on LVEntryVersion (groupId, version);
create unique index IX_259C6F9D on LVEntryVersion (lvEntryId, version, companyId);
create index IX_4B556E5E on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version);
create unique index IX_4665330C on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version, companyId);
create index IX_FA76694A on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], version);

create unique index IX_BF2FC0D0 on LazyBlobEntity (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_2E833843 on LocalizedEntryLocalization (localizedEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_6770C47D on VersionedEntry (groupId, head);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);