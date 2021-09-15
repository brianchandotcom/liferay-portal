create table SXPBlueprint (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	sxpBlueprintId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	statusByUserId LONG,
	statusByUserName VARCHAR(75) null,
	statusDate DATE null,
	title STRING null,
	description STRING null,
	configurationJSON VARCHAR(75) null,
	selectedElementsJSON VARCHAR(75) null
);

create table SXPElement (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	sxpElementId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	status INTEGER,
	title STRING null,
	description STRING null,
	configurationJSON VARCHAR(75) null,
	hidden_ BOOLEAN,
	readOnly BOOLEAN,
	type_ INTEGER
);