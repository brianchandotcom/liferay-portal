create index IX_EE41CBEB on SyncDLObject (treePath[$COLUMN_LENGTH:4000$], event[$COLUMN_LENGTH:75$]);

alter table SyncDLObject add lastPermissionChangeDate DATE;

create table SyncDevice (
	uuid_ VARCHAR(75) null,
	syncDeviceId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	type_ VARCHAR(75) null,
	buildNumber LONG,
	featureSet INTEGER,
	status INTEGER
);

create index IX_176DF87B on SyncDevice (companyId, userName[$COLUMN_LENGTH:75$]);
create index IX_AE38DEAB on SyncDevice (uuid_[$COLUMN_LENGTH:75$], companyId);

COMMIT_TRANSACTION;