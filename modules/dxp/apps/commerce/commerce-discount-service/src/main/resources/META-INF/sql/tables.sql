create table CommerceDiscount (
	uuid_ VARCHAR(75) null,
	commerceDiscountId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	target VARCHAR(75) null,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null,
	useCouponCode BOOLEAN,
	couponCode VARCHAR(75) null,
	limitationType VARCHAR(75) null,
	limitationTimes INTEGER,
	numberOfUse INTEGER,
	cumulative BOOLEAN,
	usePercentage BOOLEAN,
	level1 DECIMAL(30, 16) null,
	level2 DECIMAL(30, 16) null,
	level3 DECIMAL(30, 16) null,
	maximumDiscountAmount DECIMAL(30, 16) null,
	active_ BOOLEAN,
	displayDate DATE null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table CommerceDiscountRule (
	commerceDiscountRuleId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceDiscountId LONG,
	type_ VARCHAR(75) null,
	typeSettings VARCHAR(75) null
);

create table CommerceDiscountUserSegmentRel (
	CDiscountUserSegmentRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceDiscountId LONG,
	commerceUserSegmentEntryId LONG
);