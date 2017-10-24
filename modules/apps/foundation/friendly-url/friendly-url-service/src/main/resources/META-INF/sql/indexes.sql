create index IX_F3DC928B on FriendlyURLEntry (groupId, classNameId, classPK);
create index IX_20861768 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_63FD57EA on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_68BE94B1 on FriendlyURLEntryLocalization (friendlyURLEntryId, languageId[$COLUMN_LENGTH:75$]);
create unique index IX_8AB5CAE on FriendlyURLEntryLocalization (groupId, classNameId, urlTitle[$COLUMN_LENGTH:255$]);

create unique index IX_3B5E645B on FriendlyURLEntryMapping (classNameId, classPK);