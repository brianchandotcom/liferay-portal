create index IX_6A6C1C85 on DDLRecord (companyId);
create index IX_AAC564D3 on DDLRecord (recordSetId, userId);
create index IX_384AB6F7 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B4328F39 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_56DAB121 on DDLRecordSet (groupId, recordSetKey[$COLUMN_LENGTH:75$]);
create index IX_5938C39F on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_270BA5E1 on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_762ADC7 on DDLRecordVersion (recordId, status);
create unique index IX_C79E347 on DDLRecordVersion (recordId, version[$COLUMN_LENGTH:75$]);