create table CDataIntegrationProcess (
	CDataIntegrationProcessId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	system_ BOOLEAN,
	active_ BOOLEAN,
	cronExpression VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null
);

create table CDataIntegrationProcessLog (
	CDataIntegrationProcessLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CDataIntegrationProcessId LONG,
	error TEXT null,
	output_ TEXT null,
	startDate DATE null,
	endDate DATE null,
	status INTEGER
);