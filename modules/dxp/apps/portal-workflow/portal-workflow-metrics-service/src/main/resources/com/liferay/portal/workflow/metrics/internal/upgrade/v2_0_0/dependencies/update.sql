create table WMSLADefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	wmSLADefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	calendarKey VARCHAR(75) null,
	description TEXT null,
	duration LONG,
	name STRING null,
	pauseNodeKeys VARCHAR(75) null,
	processId LONG,
	processVersion VARCHAR(75) null,
	startNodeKeys VARCHAR(75) null,
	stopNodeKeys VARCHAR(75) null,
	version VARCHAR(75) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table WMSLADefinitionVersion (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	wmSLADefinitionVersionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	calendarKey VARCHAR(75) null,
	description VARCHAR(75) null,
	duration LONG,
	name VARCHAR(75) null,
	pauseNodeKeys VARCHAR(75) null,
	processId LONG,
	processVersion VARCHAR(75) null,
	startNodeKeys VARCHAR(75) null,
	stopNodeKeys VARCHAR(75) null,
	version VARCHAR(75) null,
	wmSLADefinitionId LONG,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_C1C90A3E on WMSLADefinition (companyId, name[$COLUMN_LENGTH:4000$], processId);
create index IX_9959E720 on WMSLADefinition (companyId, processId, processVersion[$COLUMN_LENGTH:75$], status);
create index IX_4E8F5783 on WMSLADefinition (companyId, processId, status);
create index IX_73175D43 on WMSLADefinition (companyId, status);
create index IX_41DD251F on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_285A6761 on WMSLADefinition (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F056686D on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DB48262F on WMSLADefinitionVersion (uuid_[$COLUMN_LENGTH:75$], groupId);
create index IX_7A303031 on WMSLADefinitionVersion (wmSLADefinitionId, version[$COLUMN_LENGTH:75$]);