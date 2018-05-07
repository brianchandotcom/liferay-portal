create table AssetTagStats (
	tagStatsId LONG not null primary key,
	companyId LONG,
	tagId LONG,
	classNameId LONG,
	assetCount INTEGER
);