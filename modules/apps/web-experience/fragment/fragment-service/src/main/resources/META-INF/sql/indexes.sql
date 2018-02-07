create unique index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status);
create unique index IX_18F9DFE on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$]);

create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK);
create index IX_91F46485 on FragmentEntryLink (groupId, fragmentEntryId);