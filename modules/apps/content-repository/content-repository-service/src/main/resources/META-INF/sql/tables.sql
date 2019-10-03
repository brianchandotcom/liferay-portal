create table ContentRepositoryEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	contentRepositoryEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG
);