create index IX_106FBFC3 on RedirectEntry (groupId, destinationURL[$COLUMN_LENGTH:4000$]);
create unique index IX_5040C136 on RedirectEntry (groupId, sourceURL[$COLUMN_LENGTH:4000$]);
create index IX_5A2CF310 on RedirectEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1576FD92 on RedirectEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_84671762 on RedirectNotFoundEntry (groupId, url[$COLUMN_LENGTH:4000$]);