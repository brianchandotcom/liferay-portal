create table ObjectDefinition (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectDefinitionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	dbTableName VARCHAR(75) null,
	label STRING null,
	name VARCHAR(75) null,
	panelAppOrder VARCHAR(75) null,
	panelCategoryKey VARCHAR(75) null,
	pkObjectFieldDBColumnName VARCHAR(75) null,
	pkObjectFieldName VARCHAR(75) null,
	pluralLabel STRING null,
	scope VARCHAR(75) null,
	system_ BOOLEAN,
	version INTEGER,
	status INTEGER
);

create table ObjectEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	externalReferenceCode VARCHAR(75) null,
	objectDefinitionId LONG,
	lastPublishDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table ObjectField (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectFieldId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	objectDefinitionId LONG,
	dbColumnName VARCHAR(75) null,
	dbTableName VARCHAR(75) null,
	indexed BOOLEAN,
	indexedAsKeyword BOOLEAN,
	indexedLanguageId VARCHAR(75) null,
	label STRING null,
	name VARCHAR(75) null,
	pluralLabel STRING null,
	required BOOLEAN,
	type_ VARCHAR(75) null
);

create table ObjectLayout (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectLayoutId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table ObjectLayoutBox (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectLayoutBoxId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table ObjectLayoutTab (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectLayoutTabId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);

create table ObjectRelationship (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	objectRelationshipId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);