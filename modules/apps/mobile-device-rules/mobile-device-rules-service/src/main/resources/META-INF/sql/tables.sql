create table MDRAction (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	actionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	ruleGroupInstanceId LONG,
	name STRING null,
	description STRING null,
	type_ VARCHAR(255) null,
	typeSettings TEXT null,
	lastPublishDate DATE null
);

create table MDRRule (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	ruleId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ruleGroupId LONG,
	name STRING null,
	description STRING null,
	type_ VARCHAR(255) null,
	typeSettings TEXT null,
	lastPublishDate DATE null
);

create table MDRRuleGroup (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	ruleGroupId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	lastPublishDate DATE null
);

create table MDRRuleGroupInstance (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	ruleGroupInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	ruleGroupId LONG,
	priority INTEGER,
	lastPublishDate DATE null
);