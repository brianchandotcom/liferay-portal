create table LayoutPageTemplate (
	layoutPageTemplateId LONG not null primary key,
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
	groupId LONG not null,
	layoutPageTemplateId LONG not null,
	fragmentEntryId LONG not null,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	position INTEGER,
	primary key (groupId, layoutPageTemplateId, fragmentEntryId)
);