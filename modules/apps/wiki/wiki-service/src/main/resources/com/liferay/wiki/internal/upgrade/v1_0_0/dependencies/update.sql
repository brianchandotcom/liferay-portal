alter table WikiPageResource add groupId LONG;

create unique index IX_F705C7A9 on WikiPageResource (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;