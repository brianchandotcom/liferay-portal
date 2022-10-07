create table ListTypeDefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	listTypeDefinitionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null
);

create table ListTypeEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	listTypeEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	listTypeDefinitionId LONG,
	key_ VARCHAR(75) null,
	name STRING null,
	type_ VARCHAR(75) null
);