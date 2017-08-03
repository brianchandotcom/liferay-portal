create table KaleoDraftDefinition (
	kaleoDraftDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	title STRING null,
	content TEXT null,
	version INTEGER,
	draftVersion INTEGER
);