create index IX_181962D1 on DDLRecord (className[$COLUMN_LENGTH:300$], classPK, ctCollectionId);
create index IX_245F48E3 on DDLRecord (companyId, ctCollectionId);
create index IX_608D39F7 on DDLRecord (recordSetId, ctCollectionId);
create index IX_D8611032 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_ECEA3531 on DDLRecord (recordSetId, userId, ctCollectionId);
create index IX_916EBF55 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_9C748CEF on DDLRecord (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_7E71D397 on DDLRecord (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_559DA7DE on DDLRecordSet (DDMStructureId, ctCollectionId);
create index IX_9CA54EFD on DDLRecordSet (groupId, ctCollectionId);
create unique index IX_D0A9257F on DDLRecordSet (groupId, recordSetKey[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_FC3E7BFD on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_791C2947 on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_A7D89A3F on DDLRecordSet (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_36FC2141 on DDLRecordSetVersion (recordSetId, ctCollectionId);
create index IX_3F604127 on DDLRecordSetVersion (recordSetId, status, ctCollectionId);
create unique index IX_577F80E3 on DDLRecordSetVersion (recordSetId, version[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_6866D43F on DDLRecordVersion (recordId, ctCollectionId);
create index IX_17DF1625 on DDLRecordVersion (recordId, status, ctCollectionId);
create unique index IX_8EDB4BA5 on DDLRecordVersion (recordId, version[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_DAC9A054 on DDLRecordVersion (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_3CFDFCC0 on DDLRecordVersion (userId, recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], status, ctCollectionId);