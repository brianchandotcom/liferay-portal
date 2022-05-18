create table OAuthClientASLocalMetadata (
	mvccVersion LONG default 0 not null,
	oAuthClientASLocalMetadataId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	localWellKnownURI VARCHAR(256) null,
	metadataJSON TEXT null
);

create table OAuthClientEntry (
	mvccVersion LONG default 0 not null,
	oAuthClientEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	authServerWellKnownURI VARCHAR(256) null,
	clientId VARCHAR(128) null,
	infoJSON TEXT null,
	parametersJSON TEXT null
);