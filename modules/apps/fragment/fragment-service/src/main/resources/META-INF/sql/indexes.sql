create unique index IX_19562E6B on FragmentCollection (groupId, fragmentCollectionKey[$COLUMN_LENGTH:75$]);
create index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);

create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status);
create unique index IX_62913C70 on FragmentEntry (groupId, fragmentCollectionId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create index IX_9EC6FEE4 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_BD18F965 on FragmentEntry (groupId, fragmentCollectionId, status);
create unique index IX_7F3F0EB3 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$]);

create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK);
create index IX_2040D4E9 on FragmentEntryLink (groupId, fragmentEntryId, classNameId);
create index IX_9266C536 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA2B2138 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], groupId);