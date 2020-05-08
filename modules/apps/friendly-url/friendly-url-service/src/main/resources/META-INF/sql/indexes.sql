create index IX_FE1EF2E9 on FriendlyURLEntry (groupId, classNameId, classPK, ctCollectionId);
create index IX_F1E51DC6 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_3328CB1E on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_D51F1A48 on FriendlyURLEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_4F41A5C8 on FriendlyURLEntryLocalization (friendlyURLEntryId, ctCollectionId);
create unique index IX_BBF3E90F on FriendlyURLEntryLocalization (friendlyURLEntryId, languageId[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_C753170C on FriendlyURLEntryLocalization (groupId, classNameId, urlTitle[$COLUMN_LENGTH:255$], ctCollectionId);

create unique index IX_5BE324B9 on FriendlyURLEntryMapping (classNameId, classPK, ctCollectionId);