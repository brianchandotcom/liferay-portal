create table DispatchLog (
	mvccVersion LONG default 0 not null,
	dispatchLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	dispatchTriggerId LONG,
	endDate DATE null,
	error TEXT null,
	output_ TEXT null,
	startDate DATE null,
	status INTEGER
);

create table DispatchTrigger (
	mvccVersion LONG default 0 not null,
	dispatchTriggerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	cronExpression VARCHAR(75) null,
	dispatchTaskClusterMode INTEGER,
	dispatchTaskExecutorType VARCHAR(75) null,
	dispatchTaskSettings TEXT null,
	endDate DATE null,
	name VARCHAR(75) null,
	overlapAllowed BOOLEAN,
	startDate DATE null,
	system_ BOOLEAN
);