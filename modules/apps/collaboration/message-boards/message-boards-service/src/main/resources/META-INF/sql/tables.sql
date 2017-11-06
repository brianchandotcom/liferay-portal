create table MBBan (
	uuid_ VARCHAR(75) null,
	banId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	banUserId LONG,
	lastPublishDate DATE null
);

create table MBStatsUser (
	statsUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	messageCount INTEGER,
	lastPostDate DATE null
);