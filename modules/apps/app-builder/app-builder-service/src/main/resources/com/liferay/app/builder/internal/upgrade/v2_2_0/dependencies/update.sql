create table AppBuilderAppDataRecordLink (
	appBuilderAppDataRecordLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	appBuilderAppId LONG,
	appBuilderAppVersionId LONG,
	ddlRecordId LONG
);

create index IX_22C0B8B0 on AppBuilderAppDataRecordLink (appBuilderAppId);
create index IX_CF7CED86 on AppBuilderAppDataRecordLink (ddlRecordId);

create table AppBuilderAppVersion (
	uuid_ VARCHAR(75) null,
	appBuilderAppVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	appBuilderAppId LONG,
	ddlRecordSetId LONG,
	ddmStructureId LONG,
	ddmStructureLayoutId LONG,
	version VARCHAR(75) null
);

create index IX_2AEAD6CD on AppBuilderAppVersion (appBuilderAppId, version[$COLUMN_LENGTH:75$]);
create index IX_254A3ED1 on AppBuilderAppVersion (companyId);
create index IX_17464593 on AppBuilderAppVersion (groupId);
create index IX_E67BA72B on AppBuilderAppVersion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9A89FC6D on AppBuilderAppVersion (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;