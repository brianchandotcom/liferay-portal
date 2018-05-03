create unique index IX_73B13580 on ReadingTimeEntry (groupId, classNameId, classPK);
create index IX_29FACA53 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_D647C995 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], groupId);