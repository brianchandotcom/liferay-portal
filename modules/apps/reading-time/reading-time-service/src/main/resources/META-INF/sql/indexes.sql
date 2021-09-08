create unique index IX_7010BDE on ReadingTimeEntry (groupId, classNameId, classPK, ctCollectionId);
create index IX_BBA49AB1 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_41D63313 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_4FDED5F3 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);