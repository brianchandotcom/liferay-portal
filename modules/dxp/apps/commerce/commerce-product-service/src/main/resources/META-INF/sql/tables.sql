create table CPAttachmentFileEntry (
	uuid_ VARCHAR(75) null,
	CPAttachmentFileEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	fileEntryId LONG,
	displayDate DATE null,
	expirationDate DATE null,
	title STRING null,
	json TEXT null,
	priority DOUBLE,
	type_ INTEGER,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CPDSpecificationOptionValue (
	uuid_ VARCHAR(75) null,
	CPDSpecificationOptionValueId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	CPSpecificationOptionId LONG,
	CPOptionCategoryId LONG,
	value STRING null,
	priority DOUBLE,
	lastPublishDate DATE null
);

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
	ignoreSKUCombinations BOOLEAN,
	shippable BOOLEAN,
	freeShipping BOOLEAN,
	shipSeparately BOOLEAN,
	shippingExtraPrice DOUBLE,
	width DOUBLE,
	height DOUBLE,
	depth DOUBLE,
	weight DOUBLE,
	CPTaxCategoryId LONG,
	taxExempt BOOLEAN,
	telcoOrElectronics BOOLEAN,
	DDMStructureKey VARCHAR(75) null,
	published BOOLEAN,
	externalReferenceCode VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	defaultLanguageId VARCHAR(75) null
);

create table CPDefinitionLink (
	uuid_ VARCHAR(75) null,
	CPDefinitionLinkId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId1 LONG,
	CPDefinitionId2 LONG,
	priority DOUBLE,
	type_ VARCHAR(75) null
);

create table CPDefinitionLocalization (
	mvccVersion LONG default 0 not null,
	cpDefinitionLocalizationId LONG not null primary key,
	companyId LONG,
	CPDefinitionId LONG,
	languageId VARCHAR(75) null,
	title STRING null,
	shortDescription STRING null,
	description TEXT null,
	metaTitle VARCHAR(255) null,
	metaKeywords VARCHAR(255) null,
	metaDescription VARCHAR(255) null
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
	title STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	priority DOUBLE,
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
	title STRING null,
	priority DOUBLE,
	key_ VARCHAR(75) null
);

create table CPDisplayLayout (
	uuid_ VARCHAR(75) null,
	CPDisplayLayoutId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	layoutUuid VARCHAR(75) null
);

create table CPFriendlyURLEntry (
	uuid_ VARCHAR(75) null,
	CPFriendlyURLEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	languageId VARCHAR(75) null,
	urlTitle VARCHAR(255) null,
	main BOOLEAN
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
	gtin VARCHAR(75) null,
	manufacturerPartNumber VARCHAR(75) null,
	purchasable BOOLEAN,
	DDMContent TEXT null,
	width DOUBLE,
	height DOUBLE,
	depth DOUBLE,
	weight DOUBLE,
	cost DECIMAL(30, 16) null,
	price DECIMAL(30, 16) null,
	promoPrice DECIMAL(30, 16) null,
	published BOOLEAN,
	externalReferenceCode VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CPMeasurementUnit (
	uuid_ VARCHAR(75) null,
	CPMeasurementUnitId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	key_ VARCHAR(75) null,
	rate DOUBLE,
	primary_ BOOLEAN,
	priority DOUBLE,
	type_ INTEGER,
	lastPublishDate DATE null
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
	title STRING null,
	description STRING null,
	DDMFormFieldTypeName VARCHAR(75) null,
	facetable BOOLEAN,
	required BOOLEAN,
	skuContributor BOOLEAN,
	key_ VARCHAR(75) null,
	lastPublishDate DATE null
);

create table CPOptionCategory (
	uuid_ VARCHAR(75) null,
	CPOptionCategoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	priority DOUBLE,
	key_ VARCHAR(75) null,
	lastPublishDate DATE null
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
	priority DOUBLE,
	key_ VARCHAR(75) null,
	lastPublishDate DATE null
);

create table CPRule (
	CPRuleId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	active_ BOOLEAN,
	type_ VARCHAR(75) null
);

create table CPRuleAssetCategoryRel (
	CPRuleAssetCategoryRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPRuleId LONG,
	assetCategoryId LONG
);

create table CPRuleUserSegmentRel (
	CPRuleUserSegmentRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPRuleId LONG,
	commerceUserSegmentEntryId LONG
);

create table CPSpecificationOption (
	uuid_ VARCHAR(75) null,
	CPSpecificationOptionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPOptionCategoryId LONG,
	title STRING null,
	description STRING null,
	facetable BOOLEAN,
	key_ VARCHAR(75) null,
	lastPublishDate DATE null
);

create table CPTaxCategory (
	CPTaxCategoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null
);