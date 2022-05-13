create table OAuthClientAuthServer (
	mvccVersion LONG default 0 not null,
	oAuthClientAuthServerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	discoveryEndpoint VARCHAR(256) null,
	issuer VARCHAR(128) null,
	metadataJSON TEXT null,
	type_ VARCHAR(75) null
);

create table OAuthClientEntry (
	mvccVersion LONG default 0 not null,
	oAuthClientEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	authServerIssuer VARCHAR(128) null,
	clientId VARCHAR(128) null,
	infoJSON TEXT null,
	parametersJSON TEXT null
);