create table LayoutPageTemplateEntry (
	layoutPageTemplateEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutPageTemplateFolderId LONG,
	name VARCHAR(75) null
);

create table LayoutPageTemplateFolder (
	layoutPageTemplateFolderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table LayoutPageTemplateFragment (
	layoutPageTemplateFragmentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutPageTemplateEntryId LONG,
	fragmentEntryId LONG,
	position INTEGER
);