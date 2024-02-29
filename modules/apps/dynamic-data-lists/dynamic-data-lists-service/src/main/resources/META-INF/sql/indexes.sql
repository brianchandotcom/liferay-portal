create index IX_D443D273 on DDLRecord (className[$COLUMN_LENGTH:300$], classPK);
create index IX_6A6C1C85 on DDLRecord (companyId);
create index IX_F12C61D4 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);
create index IX_AAC564D3 on DDLRecord (recordSetId, userId);
create unique index IX_7E71D397 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_6705D180 on DDLRecordSet (DDMStructureId);
create unique index IX_2C896CFF on DDLRecordSet (groupId, ctCollectionId, recordSetKey[$COLUMN_LENGTH:75$]);
create unique index IX_D7D47417 on DDLRecordSet (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);

create index IX_1C4E1CC9 on DDLRecordSetVersion (recordSetId, status);
create unique index IX_577F80E3 on DDLRecordSetVersion (recordSetId, version[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_762ADC7 on DDLRecordVersion (recordId, status);
create unique index IX_8EDB4BA5 on DDLRecordVersion (recordId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_89A5738A on DDLRecordVersion (status, recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], userId);