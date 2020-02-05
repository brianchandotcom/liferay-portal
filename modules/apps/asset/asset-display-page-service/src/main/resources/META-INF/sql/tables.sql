create table AssetDisplayPageEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	assetDisplayPageEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	layoutPageTemplateEntryId LONG,
	type_ INTEGER,
	plid LONG,
	primary key (assetDisplayPageEntryId, ctCollectionId)
);