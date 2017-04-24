alter table DDLRecordVersion add lastPublishDate DATE null;
alter table DDLRecordVersion add modifiedDate DATE null;
alter table DDLRecordVersion add uuid_ VARCHAR(75) null;

create index IX_85B19395 on DDLRecordVersion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_C043FB57 on DDLRecordVersion (uuid_[$COLUMN_LENGTH:75$], groupId);