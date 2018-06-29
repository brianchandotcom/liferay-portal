create table DDMFormInstance (
	uuid_ VARCHAR(75) null,
	formInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	structureId LONG,
	version VARCHAR(75) null,
	name STRING null,
	description STRING null,
	settings_ TEXT null,
	lastPublishDate DATE null
);

create index IX_9E1C31FE on DDMFormInstance (groupId);
create index IX_E418320 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA9051A2 on DDMFormInstance (uuid_[$COLUMN_LENGTH:75$], groupId);

create table DDMFormInstanceRecord (
	uuid_ VARCHAR(75) null,
	formInstanceRecordId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	versionUserId LONG,
	versionUserName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	formInstanceId LONG,
	formInstanceVersion VARCHAR(75) null,
	storageId LONG,
	version VARCHAR(75) null,
	lastPublishDate DATE null
);

create index IX_5BC982B on DDMFormInstanceRecord (companyId);
create index IX_242301EA on DDMFormInstanceRecord (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$]);
create index IX_3C8DBDFF on DDMFormInstanceRecord (formInstanceId, userId);
create index IX_E1971FF on DDMFormInstanceRecord (userId, formInstanceId);
create index IX_CF8CF491 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_AA3B6B53 on DDMFormInstanceRecord (uuid_[$COLUMN_LENGTH:75$], groupId);

create table DDMFormInstanceRecordVersion (
	formInstanceRecordVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	formInstanceId LONG,
	formInstanceVersion VARCHAR(75) null,
	formInstanceRecordId LONG,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	storageId LONG
);

create index IX_EAAF6D80 on DDMFormInstanceRecordVersion (formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$]);
create index IX_B5A3FAC6 on DDMFormInstanceRecordVersion (formInstanceRecordId, status);
create unique index IX_26623628 on DDMFormInstanceRecordVersion (formInstanceRecordId, version[$COLUMN_LENGTH:75$]);
create index IX_57CA016C on DDMFormInstanceRecordVersion (userId, formInstanceId, formInstanceVersion[$COLUMN_LENGTH:75$], status);

create table DDMFormInstanceVersion (
	formInstanceVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	formInstanceId LONG,
	structureVersionId LONG,
	name STRING null,
	description STRING null,
	settings_ TEXT null,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_EB92EF26 on DDMFormInstanceVersion (formInstanceId, status);
create unique index IX_AE51CDC8 on DDMFormInstanceVersion (formInstanceId, version[$COLUMN_LENGTH:75$]);
