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

create table MBCategory (
	uuid_ VARCHAR(75) null,
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCategoryId LONG,
	name VARCHAR(75) null,
	description STRING null,
	displayStyle VARCHAR(75) null,
	threadCount INTEGER,
	messageCount INTEGER,
	lastPostDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table MBDiscussion (
	uuid_ VARCHAR(75) null,
	discussionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	threadId LONG,
	lastPublishDate DATE null
);

create table MBMailingList (
	uuid_ VARCHAR(75) null,
	mailingListId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	emailAddress VARCHAR(254) null,
	inProtocol VARCHAR(75) null,
	inServerName VARCHAR(75) null,
	inServerPort INTEGER,
	inUseSSL BOOLEAN,
	inUserName VARCHAR(75) null,
	inPassword VARCHAR(75) null,
	inReadInterval INTEGER,
	outEmailAddress VARCHAR(254) null,
	outCustom BOOLEAN,
	outServerName VARCHAR(75) null,
	outServerPort INTEGER,
	outUseSSL BOOLEAN,
	outUserName VARCHAR(75) null,
	outPassword VARCHAR(75) null,
	allowAnonymous BOOLEAN,
	active_ BOOLEAN
);

create table MBMessage (
	uuid_ VARCHAR(75) null,
	messageId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	categoryId LONG,
	threadId LONG,
	rootMessageId LONG,
	parentMessageId LONG,
	treePath STRING null,
	subject VARCHAR(75) null,
	body TEXT null,
	format VARCHAR(75) null,
	anonymous BOOLEAN,
	priority DOUBLE,
	allowPingbacks BOOLEAN,
	answer BOOLEAN,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table MBStatsUser (
	statsUserId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	messageCount INTEGER,
	lastPostDate DATE null
);

create table MBThread (
	uuid_ VARCHAR(75) null,
	threadId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId LONG,
	rootMessageId LONG,
	rootMessageUserId LONG,
	title VARCHAR(75) null,
	messageCount INTEGER,
	lastPostByUserId LONG,
	lastPostDate DATE null,
	priority DOUBLE,
	question BOOLEAN,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table MBThreadFlag (
	uuid_ VARCHAR(75) null,
	threadFlagId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	threadId LONG,
	lastPublishDate DATE null
);