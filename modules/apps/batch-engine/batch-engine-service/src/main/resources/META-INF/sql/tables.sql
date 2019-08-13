create table BatchTask (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	batchTaskId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	className VARCHAR(75) null,
	version VARCHAR(75) null,
	content BLOB,
	contentType VARCHAR(75) null,
	operation VARCHAR(75) null,
	batchSize LONG,
	startTime DATE null,
	endTime DATE null,
	status VARCHAR(75) null,
	errorMessage VARCHAR(75) null
);