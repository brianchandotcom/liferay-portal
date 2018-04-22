create table CPDefinitionAvailabilityRange (
	uuid_ VARCHAR(75) null,
	CPDAvailabilityRangeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	commerceAvailabilityRangeId LONG,
	lastPublishDate DATE null
);

create table CPDefinitionInventory (
	uuid_ VARCHAR(75) null,
	CPDefinitionInventoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPDefinitionId LONG,
	CPDefinitionInventoryEngine VARCHAR(75) null,
	lowStockActivity VARCHAR(75) null,
	displayAvailability BOOLEAN,
	displayStockQuantity BOOLEAN,
	minStockQuantity INTEGER,
	backOrders BOOLEAN,
	minOrderQuantity INTEGER,
	maxOrderQuantity INTEGER,
	allowedOrderQuantities VARCHAR(75) null,
	multipleOrderQuantity INTEGER
);

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

create table CommerceAddress (
	commerceAddressId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	name VARCHAR(75) null,
	description STRING null,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	commerceRegionId LONG,
	commerceCountryId LONG,
	latitude DOUBLE,
	longitude DOUBLE,
	phoneNumber VARCHAR(75) null,
	defaultBilling BOOLEAN,
	defaultShipping BOOLEAN
);

create table CommerceAddressRestriction (
	commerceAddressRestrictionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceCountryId LONG
);

create table CommerceAvailabilityRange (
	uuid_ VARCHAR(75) null,
	commerceAvailabilityRangeId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	priority DOUBLE,
	lastPublishDate DATE null
);

create table CommerceCountry (
	uuid_ VARCHAR(75) null,
	commerceCountryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	billingAllowed BOOLEAN,
	shippingAllowed BOOLEAN,
	twoLettersISOCode VARCHAR(75) null,
	threeLettersISOCode VARCHAR(75) null,
	numericISOCode INTEGER,
	subjectToVAT BOOLEAN,
	priority DOUBLE,
	active_ BOOLEAN,
	lastPublishDate DATE null
);

create table CommerceOrder (
	uuid_ VARCHAR(75) null,
	commerceOrderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	siteGroupId LONG,
	orderOrganizationId LONG,
	orderUserId LONG,
	commerceCurrencyId LONG,
	billingAddressId LONG,
	shippingAddressId LONG,
	commercePaymentMethodId LONG,
	commerceShippingMethodId LONG,
	shippingOptionName VARCHAR(75) null,
	purchaseOrderNumber VARCHAR(75) null,
	subtotal DECIMAL(30, 16) null,
	shippingPrice DECIMAL(30, 16) null,
	total DECIMAL(30, 16) null,
	advanceStatus VARCHAR(75) null,
	paymentStatus INTEGER,
	shippingStatus INTEGER,
	orderStatus INTEGER,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceOrderItem (
	commerceOrderItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceOrderId LONG,
	CPInstanceId LONG,
	quantity INTEGER,
	shippedQuantity INTEGER,
	json TEXT null,
	title STRING null,
	sku VARCHAR(75) null,
	price DECIMAL(30, 16) null
);

create table CommerceOrderNote (
	commerceOrderNoteId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceOrderId LONG,
	content STRING null,
	restricted BOOLEAN
);

create table CommerceOrderPayment (
	commerceOrderPaymentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceOrderId LONG,
	commercePaymentMethodId LONG,
	status INTEGER,
	content TEXT null
);

create table CommercePaymentMethod (
	commercePaymentMethodId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	imageId LONG,
	engineKey VARCHAR(75) null,
	priority DOUBLE,
	active_ BOOLEAN
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

create table CommerceRegion (
	uuid_ VARCHAR(75) null,
	commerceRegionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceCountryId LONG,
	name VARCHAR(75) null,
	code_ VARCHAR(75) null,
	priority DOUBLE,
	active_ BOOLEAN,
	lastPublishDate DATE null
);

create table CommerceShipment (
	commerceShipmentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	shipmentUserId LONG,
	commerceAddressId LONG,
	commerceShippingMethodId LONG,
	commerceWarehouseId LONG,
	carrier VARCHAR(75) null,
	trackingNumber VARCHAR(75) null,
	expectedDuration INTEGER,
	status INTEGER,
	shippingDate DATE null,
	expectedDate DATE null
);

create table CommerceShipmentItem (
	commerceShipmentItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceShipmentId LONG,
	commerceOrderItemId LONG,
	quantity INTEGER
);

create table CommerceShippingMethod (
	commerceShippingMethodId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	imageId LONG,
	engineKey VARCHAR(75) null,
	priority DOUBLE,
	active_ BOOLEAN
);

create table CommerceTaxMethod (
	commerceTaxMethodId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name STRING null,
	description STRING null,
	engineKey VARCHAR(75) null,
	percentage BOOLEAN,
	active_ BOOLEAN
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

create table CommerceWarehouse (
	commerceWarehouseId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null,
	active_ BOOLEAN,
	street1 VARCHAR(75) null,
	street2 VARCHAR(75) null,
	street3 VARCHAR(75) null,
	city VARCHAR(75) null,
	zip VARCHAR(75) null,
	commerceRegionId LONG,
	commerceCountryId LONG,
	latitude DOUBLE,
	longitude DOUBLE
);

create table CommerceWarehouseItem (
	commerceWarehouseItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceWarehouseId LONG,
	CPInstanceId LONG,
	quantity INTEGER
);