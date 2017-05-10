create table CPDefinition (
	uuid_ VARCHAR(75) null,
	CPDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	baseSKU VARCHAR(75) null,
	name VARCHAR(75) null,
	productTypeName VARCHAR(75) null,
	availableIndividually BOOLEAN,
	DDMStructureKey VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	defaultLanguageId VARCHAR(75) null
);

create table CPDefinitionLocalization (
	mvccVersion LONG default 0 not null,
	cpDefinitionLocalizationId LONG not null primary key,
	companyId LONG,
	cpDefinitionPK LONG,
	languageId VARCHAR(75) null,
	title VARCHAR(75) null,
	urlTitle VARCHAR(75) null,
	description TEXT null
);

create table CPDefinitionOptionRel (
	uuid_ VARCHAR(75) null,
	CPDefinitionOptionRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	CPOptionId LONG,
	name VARCHAR(75) null,
	title STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	priority INTEGER,
	facetable BOOLEAN,
	required BOOLEAN,
	skuContributor BOOLEAN
);

create table CPDefinitionOptionValueRel (
	uuid_ VARCHAR(75) null,
	CPDefinitionOptionValueRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionOptionRelId LONG,
	name VARCHAR(75) null,
	title STRING null,
	priority INTEGER
);

create table CPInstance (
	uuid_ VARCHAR(75) null,
	CPInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	sku VARCHAR(75) null,
	DDMContent TEXT null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CPOption (
	uuid_ VARCHAR(75) null,
	CPOptionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	title STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	facetable BOOLEAN,
	required BOOLEAN,
	skuContributor BOOLEAN
);

create table CPOptionValue (
	uuid_ VARCHAR(75) null,
	CPOptionValueId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPOptionId LONG,
	name VARCHAR(75) null,
	title STRING null,
	priority INTEGER
);