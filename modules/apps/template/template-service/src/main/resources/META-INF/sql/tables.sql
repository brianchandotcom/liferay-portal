create table TemplateEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	templateEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	infoItemClassName VARCHAR(75) null,
	infoItemFormVariationKey VARCHAR(75) null,
	DDMTemplateId LONG
);