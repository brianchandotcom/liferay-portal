create table OAuth2Application (
	oAuth2ApplicationId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	userId LONG,
	userName VARCHAR(75) null,
	allowedGrantTypes VARCHAR(75) null,
	clientConfidential BOOLEAN,
	clientId VARCHAR(75) null,
	clientSecret VARCHAR(75) null,
	description VARCHAR(75) null,
	homePageURL VARCHAR(75) null,
	iconFileEntryId LONG,
	name VARCHAR(75) null,
	privacyPolicyURL VARCHAR(75) null,
	redirectURIs VARCHAR(75) null,
	scopes VARCHAR(75) null,
	features VARCHAR(75) null
);

create table OAuth2RefreshToken (
	oAuth2RefreshTokenId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	oAuth2RefreshTokenContent VARCHAR(75) null,
	oAuth2ApplicationId LONG,
	scopes VARCHAR(75) null
);

create table OAuth2ScopeGrant (
	oAuth2ScopeGrantId LONG not null,
	createDate DATE null,
	applicationName VARCHAR(75) not null,
	bundleSymbolicName VARCHAR(75) not null,
	companyId LONG not null,
	oAuth2ScopeName VARCHAR(75) not null,
	oAuth2TokenId LONG not null,
	primary key (oAuth2ScopeGrantId, applicationName, bundleSymbolicName, companyId, oAuth2ScopeName, oAuth2TokenId)
);

create table OAuth2Token (
	oAuth2TokenId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	expirationDate DATE null,
	remoteIPInfo VARCHAR(75) null,
	oAuth2TokenContent VARCHAR(75) null,
	oAuth2ApplicationId LONG,
	oAuth2TokenType VARCHAR(75) null,
	oAuth2RefreshTokenId LONG,
	scopes VARCHAR(75) null
);