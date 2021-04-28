create table CPDefinitionDiagramEntry (
	CPDefinitionDiagramEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionDiagramSettingsId LONG,
	CPInstanceUuid VARCHAR(75) null,
	CProductId LONG,
	number_ INTEGER,
	quantity INTEGER,
	positionX DOUBLE,
	positionY DOUBLE,
	radius DOUBLE
);

create table CPDefinitionDiagramSettings (
	CPDefinitionDiagramSettingsId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	assetCategoryId LONG,
	name VARCHAR(75) null
);