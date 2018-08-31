create table AssetListEntry (
	assetListEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	typeSettings TEXT null,
	title VARCHAR(75) null,
	type_ INTEGER
);