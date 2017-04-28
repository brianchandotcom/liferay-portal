create table CPDefinition (
	uuid_ VARCHAR(75) null,
	CPDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	productTypeName VARCHAR(75) null,
	availableIndividually BOOLEAN,
	DDMStructureKey VARCHAR(75) null,
	baseSKU VARCHAR(75) null,
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
	description VARCHAR(75) null
);

create table CPDefinitionMedia (
	uuid_ VARCHAR(75) null,
	CPDefinitionMediaId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	fileEntryId LONG,
	DDMContent VARCHAR(75) null,
	priority INTEGER,
	CPMediaTypeId LONG
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
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	priority INTEGER,
	facetable BOOLEAN,
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

create table CPMediaType (
	uuid_ VARCHAR(75) null,
	CPMediaTypeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	priority INTEGER
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
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	facetable BOOLEAN,
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
	title STRING null,
	priority INTEGER
);