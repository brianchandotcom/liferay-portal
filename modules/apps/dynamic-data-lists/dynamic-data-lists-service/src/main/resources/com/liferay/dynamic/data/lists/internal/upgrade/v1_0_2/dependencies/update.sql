alter table DDLRecord add recordSetVersion VARCHAR(75) null;

alter table DDLRecordSet add versionUserId LONG null;
alter table DDLRecordSet add versionUserName VARCHAR(75) null;
alter table DDLRecordSet add version VARCHAR(75) null;

alter table DDLRecordVersion add recordSetVersion VARCHAR(75) null;

create table DDLRecordSetVersion (
	mvccVersion LONG default 0 not null,
	recordSetVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	recordSetId LONG,
	DDMStructureVersionId LONG,
	name STRING null,
	description STRING null,
	settings_ TEXT null,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_F12C61D4 on DDLRecord (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);

create index IX_19AD75F6 on DDLRecordVersion (recordSetId, recordSetVersion[$COLUMN_LENGTH:75$]);
create index IX_28202A62 on DDLRecordVersion (userId, recordSetId, recordSetVersion[$COLUMN_LENGTH:75$], status);

create index IX_1C4E1CC9 on DDLRecordSetVersion (recordSetId, status);
create unique index IX_94FC5485 on DDLRecordSetVersion (recordSetId, version[$COLUMN_LENGTH:75$]);