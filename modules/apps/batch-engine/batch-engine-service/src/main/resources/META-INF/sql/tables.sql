create table BatchEngineTask (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	batchEngineTaskId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	batchSize LONG,
	className VARCHAR(75) null,
	content BLOB,
	contentType VARCHAR(75) null,
	endTime DATE null,
	errorMessage VARCHAR(75) null,
	executeStatus VARCHAR(75) null,
	operation VARCHAR(75) null,
	startTime DATE null,
	version VARCHAR(75) null
);