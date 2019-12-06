create table AppBuilderApp (
	uuid_ VARCHAR(75) null,
	appBuilderAppId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ddmStructureId LONG,
	ddmStructureLayoutId LONG,
	deDataListViewId LONG,
	name STRING null,
	status INTEGER,
	primary key (appBuilderAppId, companyId)
);

create table AppBuilderAppDeployment (
	appBuilderAppDeploymentId LONG not null,
	companyId LONG not null,
	appBuilderAppId LONG,
	settings_ TEXT null,
	type_ VARCHAR(75) null,
	primary key (appBuilderAppDeploymentId, companyId)
);