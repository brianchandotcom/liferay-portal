create table FriendlyURL (
	uuid_ VARCHAR(75) null,
	friendlyURLId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	urlTitle VARCHAR(255) null,
	main BOOLEAN
);

create table FriendlyURLLocalization (
	friendlyURLLocalizationId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	friendlyURLId LONG,
	urlTitle VARCHAR(255) null,
	languageId VARCHAR(75) null
);