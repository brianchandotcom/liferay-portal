create table TemplateEntry (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	templateEntryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	ddmTemplateId LONG,
	infoItemClassName VARCHAR(75) null,
	infoItemFormVariationKey VARCHAR(75) null,
	lastPublishDate DATE null,
	primary key (templateEntryId, ctCollectionId)
);