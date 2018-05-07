alter table KaleoAction add kaleoClassName VARCHAR(200) null;
alter table KaleoAction add kaleoClassPK LONG;

alter table KaleoDefinition add content TEXT null;
alter table kaleoDefinition add kaleoClassName VARCHAR(200) null;
alter table kaleoDefinition add kaleoClassPK LONG;

alter table KaleoLog add kaleoClassName VARCHAR(200) null;
alter table KaleoLog add kaleoClassPK LONG;

alter table KaleoNode add metadata TEXT null;

alter table KaleoTaskAssignment add assigneeActionId VARCHAR(75) null;
alter table KaleoTaskAssignment add assigneeScript TEXT null;
alter table KaleoTaskAssignment add assigneeScriptLanguage VARCHAR(75) null;
alter table KaleoTaskAssignment add kaleoClassName VARCHAR(200) null;
alter table KaleoTaskAssignment add kaleoClassPK LONG;

create table KaleoTimer (
	kaleoTimerId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(200) null,
	createDate DATE null,
	modifiedDate DATE null,
	kaleoClassName VARCHAR(200) null,
	kaleoClassPK LONG,
	kaleoDefinitionId LONG,
	name VARCHAR(75) null,
	blocking BOOLEAN,
	description STRING null,
	duration DOUBLE,
	scale VARCHAR(75) null,
	recurrenceDuration DOUBLE,
	recurrenceScale VARCHAR(75) null
);

create table KaleoTimerInstanceToken (
	kaleoTimerInstanceTokenId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(200) null,
	createDate DATE null,
	modifiedDate DATE null,
	kaleoClassName VARCHAR(200) null,
	kaleoClassPK LONG,
	kaleoDefinitionId LONG,
	kaleoInstanceId LONG,
	kaleoInstanceTokenId LONG,
	kaleoTaskInstanceTokenId LONG,
	kaleoTimerId LONG,
	kaleoTimerName VARCHAR(200) null,
	blocking BOOLEAN,
	completionUserId LONG,
	completed BOOLEAN,
	completionDate DATE null,
	workflowContext TEXT null
);

COMMIT_TRANSACTION;