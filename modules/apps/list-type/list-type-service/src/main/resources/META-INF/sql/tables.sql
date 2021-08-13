create table ListTypeDefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	listTypeDefinitionId LONG not null primary key,
	companyId LONG,
	label STRING null
);

create table ListTypeEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	listTypeEntryId LONG not null primary key,
	companyId LONG,
	listTypeDefinitionId LONG,
	label STRING null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null
);