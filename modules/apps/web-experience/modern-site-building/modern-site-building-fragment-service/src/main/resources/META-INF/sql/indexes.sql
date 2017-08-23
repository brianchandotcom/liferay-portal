create unique index IX_F0F44819 on MSBFragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_78A75BC9 on MSBFragmentEntry (fragmentCollectionId);
create index IX_FC3453DB on MSBFragmentEntry (groupId, fragmentCollectionId);
create unique index IX_30585F1D on MSBFragmentEntry (groupId, name[$COLUMN_LENGTH:75$]);