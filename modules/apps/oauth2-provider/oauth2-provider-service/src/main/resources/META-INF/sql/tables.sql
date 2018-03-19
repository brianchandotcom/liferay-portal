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
	scopeAliases TEXT null,
	tokenContent TEXT null,
	tokenType VARCHAR(75) null
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
	features STRING null,
	homePageURL STRING null,
	iconFileEntryId LONG,
	name VARCHAR(75) null,
	privacyPolicyURL STRING null,
	redirectURIs STRING null,
	scopeAliases TEXT null
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
	scopeAliases TEXT null,
	tokenContent TEXT null
);

create table OAuth2ScopeGrant (
	oAuth2ScopeGrantId LONG not null primary key,
	applicationName VARCHAR(75) null,
	bundleSymbolicName VARCHAR(75) null,
	companyId LONG,
	oAuth2AccessTokenId LONG,
	scope VARCHAR(75) null
);