create table OSBFaro_ContactsCardTemplate (
	mvccVersion LONG default 0 not null,
	contactsCardTemplateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createTime LONG,
	userId LONG,
	userName VARCHAR(75) null,
	modifiedTime LONG,
	name VARCHAR(75) null,
	settings_ STRING null,
	type_ INTEGER
);

create table OSBFaro_ContactsLayoutTemplate (
	mvccVersion LONG default 0 not null,
	contactsLayoutTemplateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createTime LONG,
	userId LONG,
	userName VARCHAR(75) null,
	modifiedTime LONG,
	headerContactsCardTemplateIds STRING null,
	name VARCHAR(75) null,
	settings_ STRING null,
	type_ INTEGER
);