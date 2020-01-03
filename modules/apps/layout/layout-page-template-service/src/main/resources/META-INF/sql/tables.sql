create table LayoutPageTemplateCollection (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutPageTemplateCollectionId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null,
	primary key (layoutPageTemplateCollectionId, companyId)
);

create table LayoutPageTemplateEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutPageTemplateEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
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
	plid LONG,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	primary key (layoutPageTemplateEntryId, companyId)
);

create table LayoutPageTemplateStructure (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	layoutPageTemplateStructureId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	primary key (layoutPageTemplateStructureId, companyId)
);

create table LayoutPageTemplateStructureRel (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	lPageTemplateStructureRelId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	layoutPageTemplateStructureId LONG,
	segmentsExperienceId LONG,
	data_ TEXT null,
	primary key (lPageTemplateStructureRelId, companyId)
);