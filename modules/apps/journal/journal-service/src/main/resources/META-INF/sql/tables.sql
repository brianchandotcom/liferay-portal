create table JournalArticle (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	resourcePrimKey LONG,
	groupId LONG,
	companyId LONG,
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
	urlTitle VARCHAR(255) null,
	content TEXT null,
	DDMStructureKey VARCHAR(75) null,
	DDMTemplateKey VARCHAR(75) null,
	defaultLanguageId VARCHAR(75) null,
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
	statusDate DATE null
);

create table JournalArticleLocalization (
	mvccVersion LONG default 0 not null,
	articleLocalizationId LONG not null primary key,
	companyId LONG,
	articlePK LONG,
	title VARCHAR(400) null,
	description STRING null,
	languageId VARCHAR(75) null
);

create table JournalArticleResource (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	resourcePrimKey LONG not null primary key,
	groupId LONG,
	companyId LONG,
	articleId VARCHAR(75) null
);

create table JournalContentSearch (
	mvccVersion LONG default 0 not null,
	contentSearchId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	privateLayout BOOLEAN,
	layoutId LONG,
	portletId VARCHAR(200) null,
	articleId VARCHAR(75) null
);

create table JournalFeed (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
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
	targetPortletId VARCHAR(200) null,
	contentField VARCHAR(75) null,
	feedFormat VARCHAR(75) null,
	feedVersion DOUBLE,
	lastPublishDate DATE null
);

create table JournalFolder (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	folderId LONG not null primary key,
	groupId LONG,
	companyId LONG,
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
	statusDate DATE null
);