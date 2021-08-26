create unique index IX_8AE0E668 on WebHookEntry (companyId, destination[$COLUMN_LENGTH:75$], url[$COLUMN_LENGTH:75$]);
create index IX_E8D6FCF5 on WebHookEntry (uuid_[$COLUMN_LENGTH:75$], companyId);