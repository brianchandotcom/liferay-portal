create unique index IX_5040C136 on RedirectEntry (groupId, sourceURL[$COLUMN_LENGTH:75$]);
create index IX_5A2CF310 on RedirectEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1576FD92 on RedirectEntry (uuid_[$COLUMN_LENGTH:75$], groupId);