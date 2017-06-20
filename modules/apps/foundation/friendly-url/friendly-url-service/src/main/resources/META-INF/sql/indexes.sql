create index IX_88D05B8 on FriendlyURLEntry (groupId, classNameId, classPK, main);
create index IX_E47CFA8 on FriendlyURLEntry (groupId, classNameId, classPK, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_2E40C535 on FriendlyURLEntry (groupId, classNameId, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_23FDB0B4 on FriendlyURLEntry (groupId, companyId, classNameId, classPK, main);
create index IX_5B329CA4 on FriendlyURLEntry (groupId, companyId, classNameId, classPK, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_B5982CB9 on FriendlyURLEntry (groupId, companyId, classNameId, urlTitle[$COLUMN_LENGTH:255$]);
create index IX_20861768 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1C7E10E0 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_33F49821 on FriendlyURLEntryLocalization (groupId, friendlyURLEntryId, languageId[$COLUMN_LENGTH:75$]);
create unique index IX_C540141 on FriendlyURLEntryLocalization (groupId, urlTitle[$COLUMN_LENGTH:255$], languageId[$COLUMN_LENGTH:75$]);