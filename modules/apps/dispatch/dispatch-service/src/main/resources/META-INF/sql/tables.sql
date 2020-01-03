create table DispatchLog (
	mvccVersion LONG default 0 not null,
	dispatchLogId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	dispatchTriggerId LONG,
	endDate DATE null,
	error VARCHAR(75) null,
	output_ VARCHAR(75) null,
	startDate DATE null,
	status INTEGER,
	primary key (dispatchLogId, companyId)
);

create table DispatchTrigger (
	mvccVersion LONG default 0 not null,
	dispatchTriggerId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	cronExpression VARCHAR(75) null,
	endDate DATE null,
	name VARCHAR(75) null,
	startDate DATE null,
	system_ BOOLEAN,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null,
	primary key (dispatchTriggerId, companyId)
);