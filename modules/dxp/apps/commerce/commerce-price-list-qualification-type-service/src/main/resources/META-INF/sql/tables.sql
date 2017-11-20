create table CommercePriceListUserRel (
	uuid_ VARCHAR(75) null,
	commercePriceListUserRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPLQualificationTypeRelId LONG,
	classNameId LONG,
	classPK LONG,
	lastPublishDate DATE null
);