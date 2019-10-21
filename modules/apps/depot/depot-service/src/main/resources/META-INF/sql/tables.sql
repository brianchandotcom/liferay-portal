create table DepotEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	depotEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null
);

create table DepotEntryGroupRel (
	mvccVersion LONG default 0 not null,
	depotEntryGroupRelId LONG not null primary key,
	companyId LONG,
	depotEntryId LONG,
	toGroupId LONG
);