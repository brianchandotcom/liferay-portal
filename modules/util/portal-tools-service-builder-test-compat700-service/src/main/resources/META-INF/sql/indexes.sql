create index IX_A2CFD25 on ArrayableEntry (groupId);

create unique index IX_B9ED835D on BasicEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_42E03820 on BasicEntry (groupId);

create unique index IX_1CF99E19 on CacheDisabledEntry (name[$COLUMN_LENGTH:75$]);

create unique index IX_91F43DDD on ConvertNullEntry (name[$COLUMN_LENGTH:75$]);

create unique index IX_6E042099 on EagerBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_F723689D on LazyBlobEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C86E9B9 on LikeFinderEntry (companyId, ownerId, ownerType, portletId[$COLUMN_LENGTH:75$]);
create unique index IX_AE0448CB on LikeFinderEntry (ownerId, ownerType, portletId[$COLUMN_LENGTH:75$]);

create unique index IX_F8852A18 on MVCCEntry (companyId, name[$COLUMN_LENGTH:75$]);

create index IX_AD13D943 on MappingEntries_BasicEntries (companyId);
create index IX_824399F8 on MappingEntries_BasicEntries (mappingEntryId);

create index IX_AB54AC01 on WhereClauseEntry (name[$COLUMN_LENGTH:75$]);