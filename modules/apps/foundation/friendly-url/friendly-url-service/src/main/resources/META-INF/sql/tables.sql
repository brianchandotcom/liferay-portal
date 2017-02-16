create table FriendlyURLEntry (
	uuid_ VARCHAR(75) null,
	friendlyURLEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	urlTitle VARCHAR(75) null,
	main BOOLEAN
);

create table FriendlyURLLocalization (
	friendlyURLLocalizationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	friendlyURLEntryId LONG,
	urlTitle VARCHAR(255) null,
	languageId VARCHAR(75) null
);