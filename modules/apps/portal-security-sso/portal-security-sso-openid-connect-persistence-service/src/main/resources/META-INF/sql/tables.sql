create table OpenIdConnectSession (
	mvccVersion LONG default 0 not null,
	openIdConnectSessionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	modifiedDate DATE null,
	accessToken TEXT null,
	accessTokenExpirationDate DATE null,
	authServerWellKnownURI VARCHAR(256) null,
	clientId VARCHAR(256) null,
	idToken TEXT null,
	refreshToken VARCHAR(2000) null
);