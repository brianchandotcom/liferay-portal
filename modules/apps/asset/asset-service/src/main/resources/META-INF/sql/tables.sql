create table AssetEntryUsage (
	uuid_ VARCHAR(75) null,
	assetEntryUsageId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	assetEntryId LONG,
	classNameId LONG,
	classPK LONG,
	portletId VARCHAR(200) null,
	lastPublishDate DATE null
);