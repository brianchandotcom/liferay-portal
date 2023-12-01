create unique index IX_7FA4CEC9 on FragmentCollection (groupId, fragmentCollectionKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_536510F5 on FragmentCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_8FB7E9C0 on FragmentCollection (uuid_[$COLUMN_LENGTH:75$]);

create index IX_5C61E2DD on FragmentComposition (fragmentCollectionId);
create index IX_3063052C on FragmentComposition (groupId, fragmentCollectionId, status, name[$COLUMN_LENGTH:75$]);
create unique index IX_3F7591A1 on FragmentComposition (groupId, fragmentCompositionKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_70029354 on FragmentComposition (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_25B5B355 on FragmentEntry (ctCollectionId, headId);
create index IX_ADC3EFB9 on FragmentEntry (fragmentCollectionId, head);
create index IX_8B622592 on FragmentEntry (groupId, fragmentCollectionId, head, name[$COLUMN_LENGTH:75$]);
create index IX_24159CF8 on FragmentEntry (groupId, fragmentCollectionId, head, status, name[$COLUMN_LENGTH:75$]);
create index IX_14AA0B48 on FragmentEntry (groupId, fragmentCollectionId, head, type_, status);
create index IX_18F9DFE on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_BE29E964 on FragmentEntry (groupId, fragmentCollectionId, status, name[$COLUMN_LENGTH:75$]);
create index IX_BD1F4C5C on FragmentEntry (groupId, fragmentCollectionId, type_, status);
create index IX_7F3F0EB3 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create unique index IX_1676C265 on FragmentEntry (groupId, head, fragmentEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_515CC759 on FragmentEntry (head, type_);
create index IX_40CE21AD on FragmentEntry (type_);
create index IX_6E7DE18C on FragmentEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_67FF823E on FragmentEntryLink (fragmentEntryId, deleted);
create index IX_2FB5437D on FragmentEntryLink (groupId, classNameId, classPK);
create index IX_4A9E751A on FragmentEntryLink (groupId, fragmentEntryId, classNameId, classPK);
create index IX_3D731EF6 on FragmentEntryLink (groupId, plid, deleted);
create index IX_A234739A on FragmentEntryLink (groupId, plid, fragmentEntryId);
create index IX_22C863E3 on FragmentEntryLink (groupId, plid, originalFragmentEntryLinkId);
create index IX_EECD9CBD on FragmentEntryLink (groupId, plid, segmentsExperienceId, deleted);
create index IX_EAA73980 on FragmentEntryLink (groupId, plid, segmentsExperienceId, rendererKey[$COLUMN_LENGTH:200$]);
create index IX_EB688B56 on FragmentEntryLink (groupId, segmentsExperienceId, classNameId, classPK);
create index IX_352AE29E on FragmentEntryLink (rendererKey[$COLUMN_LENGTH:200$], companyId);
create index IX_17C15BB2 on FragmentEntryLink (uuid_[$COLUMN_LENGTH:75$]);

create index IX_7A6F05CF on FragmentEntryVersion (fragmentCollectionId, version);
create index IX_391FD151 on FragmentEntryVersion (fragmentEntryId);
create index IX_2509F8CA on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$]);
create index IX_FD32B830 on FragmentEntryVersion (groupId, fragmentCollectionId, status, name[$COLUMN_LENGTH:75$]);
create index IX_5F305710 on FragmentEntryVersion (groupId, fragmentCollectionId, type_, status);
create index IX_8D72731C on FragmentEntryVersion (groupId, fragmentCollectionId, version, name[$COLUMN_LENGTH:75$]);
create index IX_986CC082 on FragmentEntryVersion (groupId, fragmentCollectionId, version, status, name[$COLUMN_LENGTH:75$]);
create index IX_2B3758FE on FragmentEntryVersion (groupId, fragmentCollectionId, version, type_, status);
create index IX_519A387F on FragmentEntryVersion (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$]);
create unique index IX_7F8EFD09 on FragmentEntryVersion (groupId, version, fragmentEntryKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_850F2979 on FragmentEntryVersion (type_);
create index IX_B2BEE958 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$]);
create unique index IX_9B8893DF on FragmentEntryVersion (version, fragmentEntryId, ctCollectionId);
create index IX_75B533A9 on FragmentEntryVersion (version, type_);