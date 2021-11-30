create index IX_1BAFC32 on RemoteAppEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_7CDD4FB0 on RemoteAppEntry (companyId, iFrameURL[$COLUMN_LENGTH:4000$]);
create index IX_5F8F9C11 on RemoteAppEntry (uuid_[$COLUMN_LENGTH:75$], companyId);