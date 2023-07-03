create table KaleoProcess (
	uuid_ VARCHAR(75) null,
	kaleoProcessId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDLRecordSetId LONG,
	DDMTemplateId LONG,
	workflowDefinitionName VARCHAR(75) null,
	workflowDefinitionVersion INTEGER
);

create table KaleoProcessLink (
	kaleoProcessLinkId LONG not null primary key,
	companyId LONG,
	kaleoProcessId LONG,
	workflowTaskName VARCHAR(75) null,
	DDMTemplateId LONG
);