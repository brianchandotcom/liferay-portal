create table OpenIdConnectSession (
	mvccVersion LONG default 0 not null,
	openIdConnectSessionId LONG not null primary key,
	companyId LONG,
	modifiedDate DATE null,
	accessToken VARCHAR(3000) null,
	idToken VARCHAR(3999) null,
	providerName VARCHAR(75) null,
	refreshToken VARCHAR(2000) null
);