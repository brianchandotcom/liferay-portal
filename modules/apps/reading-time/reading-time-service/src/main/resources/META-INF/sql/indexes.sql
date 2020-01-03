create unique index IX_81385EA8 on ReadingTimeEntry (groupId, classNameId, classPK, companyId);
create index IX_29FACA53 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1A0FDB73 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);