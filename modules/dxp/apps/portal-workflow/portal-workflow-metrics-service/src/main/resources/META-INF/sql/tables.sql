create table WorkflowMetricsSLACalendar (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLACalendarId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table WorkflowMetricsSLACondition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLAConditionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	workflowMetricsSLADefinitionId LONG
);

create table WorkflowMetricsSLADefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	workflowMetricsSLADefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	duration LONG,
	processId LONG,
	pauseNodeNames VARCHAR(75) null,
	startNodeNames VARCHAR(75) null,
	stopNodeNames VARCHAR(75) null
);