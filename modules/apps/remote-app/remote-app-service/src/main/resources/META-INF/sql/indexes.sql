create unique index IX_7CDD4FB0 on RemoteAppEntry (companyId, iFrameURL[$COLUMN_LENGTH:4000$]);
create index IX_5F8F9C11 on RemoteAppEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_46476743 on RemoteAppEntryLocalization (remoteAppEntryId, languageId[$COLUMN_LENGTH:75$]);