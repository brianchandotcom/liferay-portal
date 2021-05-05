create table BatchPlannerLog (
	mvccVersion LONG default 0 not null,
	batchPlannerLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	batchEngineTaskERC VARCHAR(75) null,
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
	contentFieldName VARCHAR(75) null,
	contentFieldType VARCHAR(75) null,
	openAPIFieldName VARCHAR(75) null,
	openAPIFieldType VARCHAR(75) null,
	transformationJavaCode TEXT null
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
	contentType VARCHAR(75) null,
	contentUrl VARCHAR(75) null,
	openAPISchemaClassName VARCHAR(75) null,
	name VARCHAR(75) null,
	export BOOLEAN
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