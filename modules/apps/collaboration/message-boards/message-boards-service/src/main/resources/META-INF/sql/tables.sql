create table MBStatsUser (
	statsUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	messageCount INTEGER,
	lastPostDate DATE null
);