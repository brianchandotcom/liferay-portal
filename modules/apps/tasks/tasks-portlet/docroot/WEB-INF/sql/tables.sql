create table TMS_TasksEntry (
	tasksEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	priority INTEGER,
	assigneeUserId LONG,
	resolverUserId LONG,
	dueDate DATE null,
	finishDate DATE null,
	status INTEGER,
	primary key (tasksEntryId, companyId)
);