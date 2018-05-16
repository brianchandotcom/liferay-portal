create table LayoutPageTemplateCollection (
	layoutPageTemplateCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null
);

create table LayoutPageTemplateEntry (
	layoutPageTemplateEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutPageTemplateCollectionId LONG,
	classNameId LONG,
	classTypeId LONG,
	name VARCHAR(75) null,
	type_ INTEGER,
	htmlPreviewEntryId LONG,
	defaultTemplate BOOLEAN,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	layoutPrototypeId LONG
);