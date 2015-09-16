create table JournalArticle (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	resourcePrimKey LONG,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	folderId LONG,
	classNameId LONG,
	classPK LONG,
	treePath STRING null,
	articleId VARCHAR(75) null,
	version DOUBLE,
	title STRING null,
	urlTitle VARCHAR(150) null,
	description TEXT null,
	content TEXT null,
	DDMStructureKey VARCHAR(75) null,
	DDMTemplateKey VARCHAR(75) null,
	layoutUuid VARCHAR(75) null,
	displayDate DATE null,
	expirationDate DATE null,
	reviewDate DATE null,
	indexable BOOLEAN,
	smallImage BOOLEAN,
	smallImageId LONG,
	smallImageURL STRING null,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	companyId LONG
);

create table JournalArticleImage (
	articleImageId LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null,
	version DOUBLE,
	elInstanceId VARCHAR(75) null,
	elName VARCHAR(75) null,
	languageId VARCHAR(75) null,
	tempImage BOOLEAN,
	companyId LONG
);

create table JournalArticleResource (
	uuid_ VARCHAR(75) null,
	resourcePrimKey LONG not null primary key,
	groupId LONG,
	articleId VARCHAR(75) null,
	companyId LONG
);

create table JournalContentSearch (
	contentSearchId LONG not null primary key,
	groupId LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	portletId VARCHAR(200) null,
	articleId VARCHAR(75) null,
	companyId LONG
);

create table JournalFeed (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	feedId VARCHAR(75) null,
	name VARCHAR(75) null,
	description STRING null,
	DDMStructureKey VARCHAR(75) null,
	DDMTemplateKey VARCHAR(75) null,
	DDMRendererTemplateKey VARCHAR(75) null,
	delta INTEGER,
	orderByCol VARCHAR(75) null,
	orderByType VARCHAR(75) null,
	targetLayoutFriendlyUrl VARCHAR(255) null,
	targetPortletId VARCHAR(75) null,
	contentField VARCHAR(75) null,
	feedFormat VARCHAR(75) null,
	feedVersion DOUBLE,
	lastPublishDate DATE null,
	companyId LONG
);

create table JournalFolder (
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	parentFolderId LONG,
	treePath STRING null,
	name VARCHAR(100) null,
	description STRING null,
	restrictionType INTEGER,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	companyId LONG
);