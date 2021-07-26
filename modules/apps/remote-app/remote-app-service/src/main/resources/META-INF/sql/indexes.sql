create unique index IX_A88F5614 on RemoteAppEntry (companyId, url[$COLUMN_LENGTH:1024$]);
create index IX_5F8F9C11 on RemoteAppEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_DAEE972A on RemoteCustomElementEntry (companyId, url[$COLUMN_LENGTH:75$]);
create index IX_7722EFA7 on RemoteCustomElementEntry (uuid_[$COLUMN_LENGTH:75$], companyId);