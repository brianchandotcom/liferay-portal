create table SchedulerProcess (
	mvccVersion LONG default 0 not null,
	schedulerProcessId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null,
	system_ BOOLEAN,
	active_ BOOLEAN,
	cronExpression VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null
);

create table SchedulerProcessLog (
	mvccVersion LONG default 0 not null,
	schedulerProcessLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	schedulerProcessId LONG,
	error VARCHAR(75) null,
	output_ VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null,
	status INTEGER
);