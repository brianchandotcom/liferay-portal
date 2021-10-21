create table BatchPlannerLog (
	mvccVersion LONG default 0 not null,
	batchPlannerLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	batchEngineExportTaskERC VARCHAR(75) null,
	batchEngineImportTaskERC VARCHAR(75) null,
	dispatchTriggerERC VARCHAR(75) null,
	size_ INTEGER,
	total INTEGER,
	status INTEGER
);

create table BatchPlannerMapping (
	mvccVersion LONG default 0 not null,
	batchPlannerMappingId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	externalFieldName VARCHAR(75) null,
	externalFieldType VARCHAR(75) null,
	internalFieldName VARCHAR(75) null,
	internalFieldType VARCHAR(75) null,
	script TEXT null
);

create table BatchPlannerPlan (
	mvccVersion LONG default 0 not null,
	batchPlannerPlanId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	export BOOLEAN,
	externalType VARCHAR(75) null,
	externalURL STRING null,
	internalClassName VARCHAR(75) null,
	name VARCHAR(75) null,
	taskItemDelegateName VARCHAR(75) null,
	template BOOLEAN
);

create table BatchPlannerPolicy (
	mvccVersion LONG default 0 not null,
	batchPlannerPolicyId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	name VARCHAR(75) null,
	value VARCHAR(75) null
);