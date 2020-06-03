create table AppBuilderApp (
	uuid_ VARCHAR(75) null,
	appBuilderAppId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	ddlRecordSetId LONG,
	ddmStructureId LONG,
	ddmStructureLayoutId LONG,
	deDataListViewId LONG,
	name STRING null,
	scope VARCHAR(75) null
);

create table AppBuilderAppDataRecordLink (
	appBuilderAppDataRecordLinkId LONG not null primary key,
	companyId LONG,
	appBuilderAppId LONG,
	ddlRecordId LONG
);

create table AppBuilderAppDeployment (
	appBuilderAppDeploymentId LONG not null primary key,
	companyId LONG,
	appBuilderAppId LONG,
	settings_ TEXT null,
	type_ VARCHAR(75) null
);