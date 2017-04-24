alter table DDLRecord add recordSetVersion VARCHAR(75) null;

alter table DDLRecordSet add versionUserId VARCHAR(75) null;
alter table DDLRecordSet add versionUserName VARCHAR(75) null;
alter table DDLRecordSet add version VARCHAR(75) null;

alter table DDLRecordVersion add recordSetVersion VARCHAR(75) null;

create table DDLRecordSetVersion (
	uuid_ VARCHAR(75) null,
	recordSetVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	recordSetId LONG,
	DDMStructureVersionId LONG,
	name STRING null,
	description STRING null,
	settings_ TEXT null,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	lastPublishDate DATE null
);

create index IX_F12C61D4 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);

create index IX_19AD75F6 on DDLRecordVersion (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);

create index IX_1C4E1CC9 on DDLRecordSetVersion (recordSetId, status);
create unique index IX_94FC5485 on DDLRecordSetVersion (recordSetId, version[$COLUMN_LENGTH:75$]);
create index IX_D6E379ED on DDLRecordSetVersion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9CEB97AF on DDLRecordSetVersion (uuid_[$COLUMN_LENGTH:75$], groupId);