create table DepotAppCustomization (
	mvccVersion LONG default 0 not null,
	depotAppCustomizationId LONG not null primary key,
	companyId LONG,
	depotEntryId LONG,
	enabled BOOLEAN,
	portletId VARCHAR(75) null
);

create table DepotEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	depotEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table DepotEntryGroupRel (
	mvccVersion LONG default 0 not null,
	depotEntryGroupRelId LONG not null primary key,
	companyId LONG,
	depotEntryId LONG,
	searchable BOOLEAN,
	toGroupId LONG
);