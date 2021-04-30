create table BatchPlannerLog (
	mvccVersion LONG default 0 not null,
	batchPlannerLogId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	batchPlannerPlanId LONG,
	batchExternalReferenceCode VARCHAR(75) null,
	dispatchExternalReferenceCode VARCHAR(75) null,
	size_ INTEGER,
	total INTEGER,
	status VARCHAR(75) null
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
	externalName VARCHAR(75) null,
	externalType VARCHAR(75) null,
	internalName VARCHAR(75) null,
	internalType VARCHAR(75) null,
	transformationExpression VARCHAR(75) null
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
	externalContentType VARCHAR(75) null,
	internalClassName VARCHAR(75) null,
	name VARCHAR(75) null,
	channel VARCHAR(75) null,
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