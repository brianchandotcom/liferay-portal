create table KaleoProcess (
	uuid_ VARCHAR(75) null,
	kaleoProcessId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	DDLRecordSetId LONG,
	DDMTemplateId LONG,
	workflowDefinitionName VARCHAR(75) null,
	workflowDefinitionVersion INTEGER,
	primary key (kaleoProcessId, companyId)
);

create table KaleoProcessLink (
	kaleoProcessLinkId LONG not null,
	companyId LONG not null,
	kaleoProcessId LONG,
	workflowTaskName VARCHAR(75) null,
	DDMTemplateId LONG,
	primary key (kaleoProcessLinkId, companyId)
);