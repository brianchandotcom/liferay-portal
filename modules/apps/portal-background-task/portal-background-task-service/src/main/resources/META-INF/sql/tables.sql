create table BackgroundTask (
	mvccVersion LONG default 0 not null,
	backgroundTaskId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(255) null,
	servletContextNames VARCHAR(255) null,
	taskExecutorClassName VARCHAR(200) null,
	taskContextMap TEXT null,
	completed BOOLEAN,
	completionDate DATE null,
	status INTEGER,
	statusMessage TEXT null,
	primary key (backgroundTaskId, companyId)
);