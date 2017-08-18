create unique index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_2095DA5 on FragmentEntry (fragmentCollectionId);
create index IX_19A0A97F on FragmentEntry (groupId, fragmentCollectionId);
create unique index IX_CACC7CC1 on FragmentEntry (groupId, name[$COLUMN_LENGTH:75$]);