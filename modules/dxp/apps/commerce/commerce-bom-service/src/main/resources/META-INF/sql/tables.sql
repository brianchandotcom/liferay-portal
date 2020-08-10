create table CBOMFolderApplicationRel (
	CBOMFolderApplicationRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceBOMFolderId LONG,
	commerceApplicationModelId LONG
);

create table CommerceBOMDefinition (
	commerceBOMDefinitionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceBOMFolderId LONG,
	CPAttachmentFileEntryId LONG,
	name VARCHAR(75) null,
	friendlyUrl VARCHAR(75) null
);

create table CommerceBOMEntry (
	commerceBOMEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	number_ INTEGER,
	CPInstanceUuid VARCHAR(75) null,
	CProductId LONG,
	commerceBOMDefinitionId LONG,
	positionX DOUBLE,
	positionY DOUBLE,
	radius DOUBLE
);

create table CommerceBOMFolder (
	commerceBOMFolderId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCommerceBOMFolderId LONG,
	name VARCHAR(75) null,
	logoId LONG,
	treePath VARCHAR(75) null
);