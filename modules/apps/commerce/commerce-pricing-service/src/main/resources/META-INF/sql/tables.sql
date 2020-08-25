create table CPricingClassCPDefinitionRel (
	CPricingClassCPDefinitionRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePricingClassId LONG,
	CPDefinitionId LONG
);

create table CommercePriceModifier (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	commercePriceModifierId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePriceListId LONG,
	title VARCHAR(75) null,
	target VARCHAR(75) null,
	modifierAmount DECIMAL(30, 16) null,
	modifierType VARCHAR(75) null,
	priority DOUBLE,
	active_ BOOLEAN,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommercePriceModifierRel (
	commercePriceModifierRelId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePriceModifierId LONG,
	classNameId LONG,
	classPK LONG
);

create table CommercePricingClass (
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	commercePricingClassId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	lastPublishDate DATE null
);