create index IX_838D8DFC on BigDecimalEntries_LVEntries (companyId);
create index IX_67100507 on BigDecimalEntries_LVEntries (lvEntryId);

create index IX_867C5A9 on BigDecimalEntry (bigDecimalValue);

create unique index IX_1CF99E19 on CacheDisabledEntry (name[$COLUMN_LENGTH:75$]);

create index IX_32F1A726 on ERCCompanyEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create index IX_DA61F9E2 on ERCGroupEntry (groupId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create unique index IX_6E042099 on EagerBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_420C1E47 on FinderWhereClauseEntry (name[$COLUMN_LENGTH:75$]);

create index IX_C28A6270 on LVEntry (groupId, head);
create unique index IX_70D6DE35 on LVEntry (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$], head);
create unique index IX_50CAD09D on LVEntry (headId);
create index IX_EAE3A996 on LVEntry (uuid_[$COLUMN_LENGTH:75$], companyId, head);
create unique index IX_91BCCF18 on LVEntry (uuid_[$COLUMN_LENGTH:75$], groupId, head);
create index IX_5355DC7A on LVEntry (uuid_[$COLUMN_LENGTH:75$], head);

create unique index IX_FC1C4C16 on LVEntryLocalization (headId);
create unique index IX_5233ABD3 on LVEntryLocalization (lvEntryId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_B05C042B on LVEntryLocalizationVersion (lvEntryId, languageId[$COLUMN_LENGTH:75$], version);
create index IX_D41B2392 on LVEntryLocalizationVersion (lvEntryId, version);
create unique index IX_EAC6D2F9 on LVEntryLocalizationVersion (lvEntryLocalizationId, version);

create unique index IX_D4DF2FAF on LVEntryVersion (groupId, uniqueGroupKey[$COLUMN_LENGTH:75$], version);
create index IX_78E84D94 on LVEntryVersion (groupId, version);
create unique index IX_4D8E2BAB on LVEntryVersion (lvEntryId, version);
create index IX_4B556E5E on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version);
create unique index IX_E9BD379C on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version);
create index IX_FA76694A on LVEntryVersion (uuid_[$COLUMN_LENGTH:75$], version);

create unique index IX_F723689D on LazyBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_2E833843 on LocalizedEntryLocalization (localizedEntryId, languageId[$COLUMN_LENGTH:75$]);

create index IX_6770C47D on VersionedEntry (groupId, head);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);