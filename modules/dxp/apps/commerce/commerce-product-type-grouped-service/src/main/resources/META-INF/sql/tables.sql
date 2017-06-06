create table CPDefinitionGroupedEntry (
	uuid_ VARCHAR(75) null,
	CPDefinitionGroupedEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	entryCPDefinitionId LONG,
	quantity INTEGER,
	priority INTEGER
);