create table OAuth2AccessToken (
	oAuth2AccessTokenId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	oAuth2ApplicationId LONG,
	oAuth2RefreshTokenId LONG,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	scopeAliases VARCHAR(75) null,
	tokenContent VARCHAR(75) null,
	tokenType VARCHAR(75) null
);

create table OAuth2Application (
	oAuth2ApplicationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
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
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	oAuth2ApplicationId LONG,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	scopeAliases VARCHAR(75) null,
	tokenContent VARCHAR(75) null
);

create table OAuth2ScopeGrant (
	oAuth2ScopeGrantId LONG not null primary key,
	companyId LONG,
	oAuth2AccessTokenId LONG,
	applicationName VARCHAR(75) null,
	bundleSymbolicName VARCHAR(75) null,
	scope VARCHAR(75) null
);