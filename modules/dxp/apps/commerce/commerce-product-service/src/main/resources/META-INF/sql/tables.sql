create table CommerceProductDefinition (
	uuid_ VARCHAR(75) null,
	commerceProductDefinitionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	urlTitle STRING null,
	description STRING null,
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
	statusDate DATE null
);

create table CommerceProductDefinitionOptionRel (
	commerceProductDefinitionOptionRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductOptionId LONG,
	commerceProductDefinitionId LONG,
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	priority VARCHAR(75) null
);

create table CommerceProductDefinitionOptionValueRel (
	commerceProductDefinitionOptionValueRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductDefinitionOptionRelId LONG,
	title STRING null,
	priority LONG
);

create table CommerceProductInstance (
	uuid_ VARCHAR(75) null,
	commerceProductInstanceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductDefinitionId LONG,
	sku VARCHAR(75) null,
	LSIN VARCHAR(75) null,
	DDMContent TEXT null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceProductOption (
	commerceProductOptionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null
);

create table CommerceProductOptionValue (
	commerceProductOptionValueId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceProductOptionId LONG,
	title STRING null,
	priority LONG
);