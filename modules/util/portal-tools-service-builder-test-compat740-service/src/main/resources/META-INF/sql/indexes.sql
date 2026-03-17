create index IX_A2CFD25 on ArrayableEntry (groupId);

create unique index IX_B9ED835D on BasicEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_42E03820 on BasicEntry (groupId);

create unique index IX_3D4C51E on CTEntry (companyId, name[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_1CF99E19 on CacheDisabledEntry (name[$COLUMN_LENGTH:75$]);

create unique index IX_91F43DDD on ConvertNullEntry (name[$COLUMN_LENGTH:75$]);

create unique index IX_1B0249DC on ERCCompanyEntry (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create index IX_84557D43 on ERCCompanyEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_DA61F9E2 on ERCGroupEntry (groupId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_17A11405 on ERCGroupEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_6EA6F478 on ERCVersionedEntry (externalReferenceCode[$COLUMN_LENGTH:75$], groupId, head);
create unique index IX_4DC13E66 on ERCVersionedEntry (headId);
create unique index IX_A1B6E921 on ERCVersionedEntry (uuid_[$COLUMN_LENGTH:75$], groupId, head);

create unique index IX_D06D55F9 on ERCVersionedEntryVersion (ercVersionedEntryId, version);
create unique index IX_1F1BC169 on ERCVersionedEntryVersion (uuid_[$COLUMN_LENGTH:75$], version, groupId);

create unique index IX_6E042099 on EagerBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_F723689D on LazyBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_AE0448CB on LikeFinderEntry (ownerId, ownerType, portletId[$COLUMN_LENGTH:75$]);

create unique index IX_2E833843 on LocalizedEntryLocalization (localizedEntryId, languageId[$COLUMN_LENGTH:75$]);

create unique index IX_F8852A18 on MVCCEntry (companyId, name[$COLUMN_LENGTH:75$]);

create index IX_AD13D943 on MappingEntries_BasicEntries (companyId);
create index IX_824399F8 on MappingEntries_BasicEntries (mappingEntryId);

create index IX_6770C47D on VersionedEntry (groupId, head);
create unique index IX_AAA6F330 on VersionedEntry (headId);

create index IX_D2594361 on VersionedEntryVersion (groupId, version);
create unique index IX_B51BCCBB on VersionedEntryVersion (versionedEntryId, version);

create index IX_AB54AC01 on WhereClauseEntry (name[$COLUMN_LENGTH:75$]);