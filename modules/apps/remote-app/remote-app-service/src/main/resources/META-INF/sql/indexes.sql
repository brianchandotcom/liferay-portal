create unique index IX_60B7390 on RemoteAppEntry (companyId, iframeURL[$COLUMN_LENGTH:1024$]);
create index IX_5F8F9C11 on RemoteAppEntry (uuid_[$COLUMN_LENGTH:75$], companyId);