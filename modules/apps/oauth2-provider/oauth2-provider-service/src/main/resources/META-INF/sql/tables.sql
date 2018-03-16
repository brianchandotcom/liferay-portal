create table OAuth2AccessToken (
	OAuth2AccessTokenId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	userId LONG,
	userName VARCHAR(75) null,
	oAuth2ApplicationId LONG,
	oAuth2RefreshTokenId LONG,
	OAuth2AccessTokenContent VARCHAR(75) null,
	OAuth2AccessTokenType VARCHAR(75) null,
	scopeAliases VARCHAR(75) null
);

create table OAuth2Application (
	oAuth2ApplicationId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	userId LONG,
	userName VARCHAR(75) null,
	allowedGrantTypes VARCHAR(75) null,
	clientId VARCHAR(75) null,
	clientProfile INTEGER,
	clientSecret VARCHAR(75) null,
	description VARCHAR(75) null,
	features VARCHAR(75) null,
	homePageURL VARCHAR(75) null,
	iconFileEntryId LONG,
	name VARCHAR(75) null,
	privacyPolicyURL VARCHAR(75) null,
	redirectURIs VARCHAR(75) null,
	scopeAliases VARCHAR(75) null
);

create table OAuth2RefreshToken (
	oAuth2RefreshTokenId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	userId LONG,
	userName VARCHAR(75) null,
	oAuth2ApplicationId LONG,
	oAuth2RefreshTokenContent VARCHAR(75) null,
	scopeAliases VARCHAR(75) null
);

create table OAuth2ScopeGrant (
	oAuth2ScopeGrantId LONG not null primary key,
	createDate DATE null,
	applicationName VARCHAR(75) null,
	bundleSymbolicName VARCHAR(75) null,
	companyId LONG,
	OAuth2AccessTokenId LONG,
	scope VARCHAR(75) null
);