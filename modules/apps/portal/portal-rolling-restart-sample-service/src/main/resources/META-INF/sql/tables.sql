create table RollingRestartSampleEntry (
	mvccVersion LONG default 0 not null,
	entryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	status INTEGER
);