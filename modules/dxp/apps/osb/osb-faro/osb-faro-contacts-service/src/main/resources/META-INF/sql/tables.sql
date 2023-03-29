create table OSBFaro_ContactsCardTemplate (
	contactsCardTemplateId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	name VARCHAR(75) null,
	settings_ STRING null,
	type_ INTEGER
);

create table OSBFaro_ContactsLayoutTemplate (
	contactsLayoutTemplateId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createTime LONG,
	modifiedTime LONG,
	headerContactsCardTemplateIds STRING null,
	name VARCHAR(75) null,
	settings_ STRING null,
	type_ INTEGER
);