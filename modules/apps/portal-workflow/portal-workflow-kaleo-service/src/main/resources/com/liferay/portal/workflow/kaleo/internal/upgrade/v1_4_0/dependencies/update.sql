create table KaleoTaskForm (
	mvccVersion LONG default 0 not null,
	kaleoTaskFormId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	kaleoDefinitionVersionId LONG,
	kaleoNodeId LONG,
	kaleoTaskId LONG,
	kaleoTaskName VARCHAR(200) null,
	name VARCHAR(200) null,
	description STRING null,
	formCompanyId LONG,
	formDefinition STRING null,
	formGroupId LONG,
	formId LONG,
	formUuid VARCHAR(75) null,
	metadata STRING null,
	priority INTEGER
);

create table KaleoTaskFormInstance (
	mvccVersion LONG default 0 not null,
	kaleoTaskFormInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	kaleoDefinitionVersionId LONG,
	kaleoInstanceId LONG,
	kaleoTaskId LONG,
	kaleoTaskInstanceTokenId LONG,
	kaleoTaskFormId LONG,
	formValues STRING null,
	formValueEntryGroupId LONG,
	formValueEntryId LONG,
	formValueEntryUuid VARCHAR(75) null,
	metadata STRING null
);

create table KaleoDefinitionVersion (
	mvccVersion LONG default 0 not null,
	kaleoDefinitionVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(200) null,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(200) null,
	title STRING null,
	description STRING null,
	content TEXT null,
	version VARCHAR(75) null,
	startKaleoNodeId LONG,
	status INTEGER
);

create unique index IX_AE02DCC on KaleoDefinitionVersion (companyId, name[$COLUMN_LENGTH:200$], version[$COLUMN_LENGTH:75$]);

alter table KaleoAction add kaleoDefinitionVersionId LONG null;
alter table KaleoCondition add kaleoDefinitionVersionId LONG null;
alter table KaleoInstance add kaleoDefinitionVersionId LONG null;
alter table KaleoInstanceToken add kaleoDefinitionVersionId LONG null;
alter table KaleoLog add kaleoDefinitionVersionId LONG null;
alter table KaleoNode add kaleoDefinitionVersionId LONG null;
alter table KaleoNotification add kaleoDefinitionVersionId LONG null;
alter table KaleoNotificationRecipient add kaleoDefinitionVersionId LONG null;
alter table KaleoTask add kaleoDefinitionVersionId LONG null;
alter table KaleoTaskAssignment add kaleoDefinitionVersionId LONG null;
alter table KaleoTaskAssignmentInstance add kaleoDefinitionVersionId LONG null;
alter table KaleoTaskInstanceToken add kaleoDefinitionVersionId LONG null;
alter table KaleoTimer add kaleoDefinitionVersionId LONG null;
alter table KaleoTimerInstanceToken add kaleoDefinitionVersionId LONG null;
alter table KaleoTransition add kaleoDefinitionVersionId LONG null;

create index IX_F8808C50 on KaleoAction (kaleoDefinitionVersionId);
create index IX_353B7FB5 on KaleoCondition (kaleoDefinitionVersionId);
create index IX_3DA1A5AC on KaleoInstance (kaleoDefinitionVersionId, completed);
create index IX_1181057E on KaleoInstanceToken (kaleoDefinitionVersionId);
create index IX_935D8E5E on KaleoLog (kaleoDefinitionVersionId);
create index IX_4B1D16B4 on KaleoNode (companyId, kaleoDefinitionVersionId);
create index IX_F066921C on KaleoNode (kaleoDefinitionVersionId);
create index IX_B8486585 on KaleoNotification (kaleoDefinitionVersionId);
create index IX_B6D98988 on KaleoNotificationRecipient (kaleoDefinitionVersionId);
create index IX_FECA871F on KaleoTask (kaleoDefinitionVersionId);
create index IX_E362B24C on KaleoTaskAssignment (kaleoDefinitionVersionId);
create index IX_B751E781 on KaleoTaskAssignmentInstance (kaleoDefinitionVersionId);
create index IX_3B8B7F83 on KaleoTaskForm (kaleoDefinitionVersionId);
create index IX_F118DB8 on KaleoTaskFormInstance (kaleoDefinitionVersionId);
create index IX_B2822979 on KaleoTaskInstanceToken (kaleoDefinitionVersionId);
create index IX_16B426EF on KaleoTransition (kaleoDefinitionVersionId);

COMMIT_TRANSACTION;