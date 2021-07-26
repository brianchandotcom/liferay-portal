create table OpenIdConnectSession (
	mvccVersion LONG default 0 not null,
	openIdConnectSessionId LONG not null primary key,
	companyId LONG,
	modifiedDate DATE null,
	accessToken VARCHAR(2048) null,
	idToken VARCHAR(2048) null,
	providerName VARCHAR(75) null,
	refreshToken VARCHAR(512) null
);