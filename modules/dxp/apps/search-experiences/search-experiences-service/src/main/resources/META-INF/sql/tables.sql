create table SXPBlueprint (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	sxpBlueprintId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	configurationJSON TEXT null,
	description STRING null,
	elementInstancesJSON TEXT null,
	schemaVersion VARCHAR(75) null,
	title STRING null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null
);

create table SXPElement (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	sxpElementId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	description STRING null,
	elementDefinitionJSON TEXT null,
	hidden_ BOOLEAN,
	readOnly BOOLEAN,
	schemaVersion VARCHAR(75) null,
	title STRING null,
	type_ INTEGER,
	status INTEGER
);