create index IX_F3DC928B on FriendlyURLEntry (groupId, classNameId, classPK);
create unique index IX_D51F1A48 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_2B00D1D3 on FriendlyURLEntryLocalization (classNameId, groupId, languageId[$COLUMN_LENGTH:75$], classPK);
create unique index IX_53B5CB4B on FriendlyURLEntryLocalization (classNameId, groupId, languageId[$COLUMN_LENGTH:75$], urlTitle[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_570320E6 on FriendlyURLEntryLocalization (classNameId, groupId, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_310462C on FriendlyURLEntryLocalization (classNameId, urlTitle[$COLUMN_LENGTH:255$], ctCollectionId, companyId);
create index IX_BFA6E36A on FriendlyURLEntryLocalization (friendlyURLEntryId);
create unique index IX_5292D20F on FriendlyURLEntryLocalization (languageId[$COLUMN_LENGTH:75$], ctCollectionId, friendlyURLEntryId);

create unique index IX_5BE324B9 on FriendlyURLEntryMapping (classNameId, classPK, ctCollectionId);