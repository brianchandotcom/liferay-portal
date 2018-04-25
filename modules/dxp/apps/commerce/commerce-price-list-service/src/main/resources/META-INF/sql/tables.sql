create table CPLUserSegmentEntryRel (
	uuid_ VARCHAR(75) null,
	CPLUserSegmentEntryRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePriceListId LONG,
	commerceUserSegmentEntryId LONG,
	order_ INTEGER,
	lastPublishDate DATE null
);

create table CommercePriceEntry (
	uuid_ VARCHAR(75) null,
	commercePriceEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPInstanceId LONG,
	commercePriceListId LONG,
	price DECIMAL(30, 16) null,
	promoPrice DECIMAL(30, 16) null,
	hasTierPrice BOOLEAN,
	lastPublishDate DATE null
);

create table CommercePriceList (
	uuid_ VARCHAR(75) null,
	commercePriceListId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentCommercePriceListId LONG,
	commerceCurrencyId LONG,
	name VARCHAR(75) null,
	priority DOUBLE,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceTierPriceEntry (
	uuid_ VARCHAR(75) null,
	commerceTierPriceEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commercePriceEntryId LONG,
	price DECIMAL(30, 16) null,
	promoPrice DECIMAL(30, 16) null,
	minQuantity INTEGER,
	lastPublishDate DATE null
);