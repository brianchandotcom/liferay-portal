create table COREntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	COREntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	description VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	name VARCHAR(75) null,
	priority INTEGER,
	type_ VARCHAR(75) null,
	typeSettings TEXT null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table COREntryRel (
	mvccVersion LONG default 0 not null,
	COREntryRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	COREntryId LONG
);