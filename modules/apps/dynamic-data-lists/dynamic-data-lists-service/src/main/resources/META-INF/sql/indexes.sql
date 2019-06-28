create index IX_6A6C1C85 on DDLRecord (companyId);
create index IX_F12C61D4 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);
create index IX_AAC564D3 on DDLRecord (recordSetId, userId);
create index IX_384AB6F7 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_B4328F39 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6705D180 on DDLRecordSet (DDMStructureId);
create unique index IX_56DAB121 on DDLRecordSet (groupId, recordSetKey[$COLUMN_LENGTH:75$]);
create index IX_5938C39F on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_270BA5E1 on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1C4E1CC9 on DDLRecordSetVersion (recordSetId, status);
create unique index IX_94FC5485 on DDLRecordSetVersion (recordSetId, version[$COLUMN_LENGTH:75$]);

create index IX_762ADC7 on DDLRecordVersion (recordId, status);
create unique index IX_C79E347 on DDLRecordVersion (recordId, version[$COLUMN_LENGTH:75$]);
create index IX_19AD75F6 on DDLRecordVersion (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);
create index IX_28202A62 on DDLRecordVersion (userId, recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], status);