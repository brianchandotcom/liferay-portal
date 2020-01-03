create index IX_F3DC928B on FriendlyURLEntry (groupId, classNameId, classPK);
create index IX_20861768 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_70F175FE on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_376A46D7 on FriendlyURLEntryLocalization (friendlyURLEntryId, languageId[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5E1B8EBA on FriendlyURLEntryLocalization (groupId, classNameId, urlTitle[$COLUMN_LENGTH:255$], companyId);

create unique index IX_9C1AE8ED on FriendlyURLEntryMapping (classNameId, classPK, companyId);