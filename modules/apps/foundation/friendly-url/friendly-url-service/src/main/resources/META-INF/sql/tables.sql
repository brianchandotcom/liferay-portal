create table FriendlyURLEntry (
	uuid_ VARCHAR(75) null,
	friendlyURLEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	urlTitle VARCHAR(255) null,
	main BOOLEAN
);

create table FriendlyURLEntryLocalization (
	friendlyURLEntryLocalizationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	friendlyURLEntryId LONG,
	urlTitle VARCHAR(255) null,
	languageId VARCHAR(75) null
);