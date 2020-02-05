create unique index IX_19562E6B on FragmentCollection (groupId, fragmentCollectionKey[$COLUMN_LENGTH:75$]);
create index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_9A228268 on FragmentCollection (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DFB882EA on FragmentCollection (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_5C61E2DD on FragmentComposition (fragmentCollectionId);
create index IX_11001AAC on FragmentComposition (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_28248B2D on FragmentComposition (groupId, fragmentCollectionId, status);
create unique index IX_86F7A143 on FragmentComposition (groupId, fragmentCompositionKey[$COLUMN_LENGTH:75$]);
create index IX_4077D454 on FragmentComposition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_68D15FD6 on FragmentComposition (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_DDB6278B on FragmentEntry (fragmentCollectionId, status);
create index IX_9EC6FEE4 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_BD18F965 on FragmentEntry (groupId, fragmentCollectionId, status);
create index IX_BD1F4C5C on FragmentEntry (groupId, fragmentCollectionId, type_, status);
create unique index IX_7F3F0EB3 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create index IX_C65BF31C on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_553E909E on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F3A29B2B on FragmentEntryLink (fragmentEntryId);
create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK);
create index IX_4A9E751A on FragmentEntryLink (groupId, fragmentEntryId, classNameId, classPK);
create index IX_51698F4A on FragmentEntryLink (rendererKey[$COLUMN_LENGTH:200$]);
create index IX_9266C536 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA2B2138 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$], groupId);