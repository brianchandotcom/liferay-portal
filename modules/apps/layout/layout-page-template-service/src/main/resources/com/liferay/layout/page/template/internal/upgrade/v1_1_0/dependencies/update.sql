create table LayoutPageTemplateCollection (
	uuid_ VARCHAR(75) null,
	layoutPageTemplateCollectionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null
);

create unique index IX_D569E8F2 on LayoutPageTemplateCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_BCD4D4B on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9E4EAA8D on LayoutPageTemplateCollection (uuid_[$COLUMN_LENGTH:75$], groupId);

create table LayoutPageTemplateEntry (
	uuid_ VARCHAR(75) null,
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
	previewFileEntryId LONG,
	defaultTemplate BOOLEAN,
	layoutPrototypeId LONG,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create index IX_957F6C5D on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status);
create index IX_E2488048 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status);
create index IX_227636E7 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status);
create index IX_1736F4A2 on LayoutPageTemplateEntry (groupId, classNameId, defaultTemplate);
create index IX_4C3A286A on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status);
create index IX_A4733F6B on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status);
create index IX_4BCAC4B0 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, type_);
create index IX_6120EE7E on LayoutPageTemplateEntry (groupId, layoutPrototypeId);
create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_1F1BEA76 on LayoutPageTemplateEntry (groupId, type_, status);
create index IX_A185457E on LayoutPageTemplateEntry (layoutPrototypeId);
create index IX_CEC0A659 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_34C0EF1B on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;