create table DepotEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	depotEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (depotEntryId, companyId)
);

create table DepotEntryGroupRel (
	mvccVersion LONG default 0 not null,
	depotEntryGroupRelId LONG not null,
	companyId LONG not null,
	depotEntryId LONG,
	searchable BOOLEAN,
	toGroupId LONG,
	primary key (depotEntryGroupRelId, companyId)
);