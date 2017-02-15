create index IX_2193A458 on FriendlyURLEntry (groupId, classNameId);
create index IX_23FDB0B4 on FriendlyURLEntry (groupId, companyId, classNameId, classPK, main);
create index IX_5B329CA4 on FriendlyURLEntry (groupId, companyId, classNameId, classPK, urlTitle[$COLUMN_LENGTH:75$]);
create index IX_B5982CB9 on FriendlyURLEntry (groupId, companyId, classNameId, urlTitle[$COLUMN_LENGTH:75$]);
create index IX_20861768 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1C7E10E0 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_4690312F on FriendlyURLLocalization (groupId, friendlyURLEntryId, languageId[$COLUMN_LENGTH:75$]);
create unique index IX_A9FD5E8B on FriendlyURLLocalization (groupId, urlTitle[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$]);