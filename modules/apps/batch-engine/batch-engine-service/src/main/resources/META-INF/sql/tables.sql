create table BatchFileImport (
	uuid_ VARCHAR(75) null,
	batchFileImportId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	fileEntryId LONG,
	batchJobExecutionId LONG,
	domainName VARCHAR(75) null,
	version VARCHAR(75) null,
	operation VARCHAR(75) null,
	callbackURL VARCHAR(75) null,
	columnNames VARCHAR(75) null,
	status VARCHAR(75) null
);

create table BatchJobExecution (
	uuid_ VARCHAR(75) null,
	batchJobExecutionId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	batchJobInstanceId LONG,
	status VARCHAR(75) null,
	startTime DATE null,
	endTime DATE null,
	jobSettings VARCHAR(75) null,
	error STRING null
);

create table BatchJobInstance (
	uuid_ VARCHAR(75) null,
	batchJobInstanceId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	jobName VARCHAR(75) null,
	jobKey VARCHAR(75) null
);