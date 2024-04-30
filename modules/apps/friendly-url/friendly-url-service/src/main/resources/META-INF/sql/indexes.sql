create index IX_F3DC928B on FriendlyURLEntry (groupId, classNameId, classPK);
create unique index IX_D51F1A48 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_BFA6E36A on FriendlyURLEntryLocalization (friendlyURLEntryId);
create index IX_543EE90B on FriendlyURLEntryLocalization (groupId, classNameId, languageId[$COLUMN_LENGTH:75$], classPK);
create unique index IX_29720B13 on FriendlyURLEntryLocalization (groupId, classNameId, languageId[$COLUMN_LENGTH:75$], urlTitle[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_8AB5CAE on FriendlyURLEntryLocalization (groupId, classNameId, urlTitle[$COLUMN_LENGTH:255$]);
create unique index IX_E46130F on FriendlyURLEntryLocalization (languageId[$COLUMN_LENGTH:75$], friendlyURLEntryId, ctCollectionId);

create unique index IX_5BE324B9 on FriendlyURLEntryMapping (classNameId, classPK, ctCollectionId);