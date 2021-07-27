create table RemoteAppEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	remoteAppEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	url VARCHAR(1024) null
);

create table RemoteCustomElementEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	remoteCustomElementEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	tagName VARCHAR(75) null,
	url VARCHAR(1024) null
);