create table FriendlyURLEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	defaultLanguageId VARCHAR(75) null,
	friendlyURLEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	primary key (friendlyURLEntryId, companyId)
);

create table FriendlyURLEntryLocalization (
	mvccVersion LONG default 0 not null,
	friendlyURLEntryLocalizationId LONG not null,
	companyId LONG not null,
	friendlyURLEntryId LONG,
	languageId VARCHAR(75) null,
	urlTitle VARCHAR(255) null,
	groupId LONG,
	classNameId LONG,
	classPK LONG,
	primary key (friendlyURLEntryLocalizationId, companyId)
);

create table FriendlyURLEntryMapping (
	mvccVersion LONG default 0 not null,
	friendlyURLEntryMappingId LONG not null,
	companyId LONG not null,
	classNameId LONG,
	classPK LONG,
	friendlyURLEntryId LONG,
	primary key (friendlyURLEntryMappingId, companyId)
);