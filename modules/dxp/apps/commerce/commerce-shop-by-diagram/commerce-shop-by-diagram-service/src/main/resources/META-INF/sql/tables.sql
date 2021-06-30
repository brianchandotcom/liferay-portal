create table CPDefinitionDiagramEntry (
	CPDefinitionDiagramEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	CPInstanceUuid VARCHAR(75) null,
	CProductId LONG,
	diagram BOOLEAN,
	number_ INTEGER,
	quantity INTEGER,
	sku VARCHAR(75) null
);

create table CPDefinitionDiagramPin (
	CPDefinitionDiagramPinId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	number_ INTEGER,
	positionX DOUBLE,
	positionY DOUBLE
);

create table CPDefinitionDiagramSetting (
	uuid_ VARCHAR(75) null,
	CPDefinitionDiagramSettingId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	CPAttachmentFileEntryId LONG,
	color VARCHAR(75) null,
	radius DOUBLE,
	type_ VARCHAR(75) null
);