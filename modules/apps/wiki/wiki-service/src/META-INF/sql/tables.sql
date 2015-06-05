create table WikiNode (
	uuid_ VARCHAR(75) null,
	nodeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPostDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table WikiPage (
	uuid_ VARCHAR(75) null,
	pageId LONG not null primary key,
	resourcePrimKey LONG,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	nodeId LONG,
	title VARCHAR(255) null,
	version DOUBLE,
	minorEdit BOOLEAN,
	content TEXT null,
	summary STRING null,
	format VARCHAR(75) null,
	head BOOLEAN,
	parentTitle VARCHAR(255) null,
	redirectTitle VARCHAR(255) null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table WikiPageResource (
	uuid_ VARCHAR(75) null,
	resourcePrimKey LONG not null primary key,
	groupId LONG,
	nodeId LONG,
	title VARCHAR(255) null
);