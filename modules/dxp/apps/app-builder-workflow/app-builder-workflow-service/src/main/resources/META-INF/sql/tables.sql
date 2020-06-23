create table AppBuilderAppWorkflowTaskLink (
	abAppWorkflowTaskLinkId LONG not null primary key,
	companyId LONG,
	appBuilderAppId LONG,
	ddmStructureLayoutId LONG,
	workflowTaskName VARCHAR(75) null
);

create table AppBuilderWorkflowTaskLink (
	mvccVersion LONG default 0 not null,
	appBuilderWorkflowTaskLinkId LONG not null primary key,
	companyId LONG,
	appBuilderAppId LONG,
	ddmStructureLayoutId LONG,
	readOnly BOOLEAN,
	workflowTaskName VARCHAR(75) null
);