DROP_TABLE_IF_EXISTS(SyncDLFileVersionDiff);

create table SyncDLFileVersionDiff (
	syncDLFileVersionDiffId LONG not null primary key,
	fileEntryId LONG,
	sourceFileVersionId LONG,
	targetFileVersionId LONG,
	dataFileEntryId LONG,
	size_ LONG,
	expirationDate DATE null
);

create index IX_A9B43C55 on SyncDLFileVersionDiff (expirationDate);
create unique index IX_AC4C7667 on SyncDLFileVersionDiff (fileEntryId, sourceFileVersionId, targetFileVersionId);

DROP_TABLE_IF_EXISTS(SyncDLObject);

create table SyncDLObject (
	syncDLObjectId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	repositoryId LONG,
	parentFolderId LONG,
	treePath STRING null,
	name VARCHAR(255) null,
	extension VARCHAR(75) null,
	mimeType VARCHAR(75) null,
	description STRING null,
	changeLog VARCHAR(75) null,
	extraSettings TEXT null,
	version VARCHAR(75) null,
	versionId LONG,
	size_ LONG,
	checksum VARCHAR(75) null,
	event VARCHAR(75) null,
	lastPermissionChangeDate DATE null,
	lockExpirationDate DATE null,
	lockUserId LONG,
	lockUserName VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typePK LONG,
	typeUuid VARCHAR(75) null
);

create index IX_8D4FDC9F on SyncDLObject (modifiedTime, repositoryId, event[$COLUMN_LENGTH:75$]);
create index IX_A3ACE372 on SyncDLObject (modifiedTime, repositoryId, parentFolderId);
create index IX_38C38A09 on SyncDLObject (repositoryId, event[$COLUMN_LENGTH:75$]);
create index IX_3BE7BB8D on SyncDLObject (repositoryId, parentFolderId, type_[$COLUMN_LENGTH:75$]);
create index IX_57F62914 on SyncDLObject (repositoryId, type_[$COLUMN_LENGTH:75$]);
create index IX_EE41CBEB on SyncDLObject (treePath[$COLUMN_LENGTH:4000$], event[$COLUMN_LENGTH:75$]);
create unique index IX_E3F57BD6 on SyncDLObject (type_[$COLUMN_LENGTH:75$], typePK);
create index IX_28CD54BB on SyncDLObject (type_[$COLUMN_LENGTH:75$], version[$COLUMN_LENGTH:75$]);
create index IX_1CCA3B5 on SyncDLObject (version[$COLUMN_LENGTH:75$], type_[$COLUMN_LENGTH:75$]);

DROP_TABLE_IF_EXISTS(SyncDevice);

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