create table OSBFaro_FaroChannel (
	faroChannelId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	channelId VARCHAR(75) null,
	name VARCHAR(75) null,
	permissionType INTEGER,
	workspaceGroupId LONG
);

create table OSBFaro_FaroNotification (
	faroNotificationId LONG not null primary key,
	groupId LONG,
	userId LONG,
	createTime LONG,
	modifiedTime LONG,
	ownerId LONG,
	scope VARCHAR(75) null,
	read_ BOOLEAN,
	type_ VARCHAR(75) null,
	subtype VARCHAR(75) null
);

create table OSBFaro_FaroPreferences (
	faroPreferencesId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	ownerId LONG,
	preferences STRING null
);

create table OSBFaro_FaroProject (
	faroProjectId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	name VARCHAR(75) null,
	accountKey VARCHAR(75) null,
	accountName VARCHAR(75) null,
	corpProjectName VARCHAR(75) null,
	corpProjectUuid VARCHAR(75) null,
	ipAddresses STRING null,
	incidentReportEmailAddresses STRING null,
	lastAccessTime LONG,
	recommendationsEnabled BOOLEAN,
	serverLocation VARCHAR(75) null,
	services STRING null,
	state_ VARCHAR(75) null,
	subscription STRING null,
	timeZoneId VARCHAR(75) null,
	weDeployKey VARCHAR(75) null
);

create table OSBFaro_FaroProjectEmailAddressDomain (
	faroProjectEmailAddressDomainId LONG not null primary key,
	groupId LONG,
	faroProjectId LONG,
	emailAddressDomain VARCHAR(75) null
);

create table OSBFaro_FaroUser (
	faroUserId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	liveUserId LONG,
	roleId LONG,
	emailAddress VARCHAR(75) null,
	key_ VARCHAR(75) null,
	status INTEGER
);