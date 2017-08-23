create table AssetDisplayTemplate (
	assetDisplayTemplateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	classNameId LONG,
	DDMTemplateId LONG,
	main BOOLEAN
);