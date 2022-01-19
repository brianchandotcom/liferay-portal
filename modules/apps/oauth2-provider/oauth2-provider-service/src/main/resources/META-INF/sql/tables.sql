create table OA2Auths_OA2ScopeGrants (
	companyId LONG not null,
	oAuth2AuthorizationId LONG not null,
	oAuth2ScopeGrantId LONG not null,
	primary key (oAuth2AuthorizationId, oAuth2ScopeGrantId)
);

create table OAuth2Application (
	oAuth2ApplicationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	oA2AScopeAliasesId LONG,
	allowedGrantTypes VARCHAR(128) null,
	clientCredentialUserId LONG,
	clientCredentialUserName VARCHAR(75) null,
	clientId VARCHAR(75) null,
	clientProfile INTEGER,
	clientSecret VARCHAR(75) null,
	description STRING null,
	features STRING null,
	homePageURL STRING null,
	iconFileEntryId LONG,
	name VARCHAR(75) null,
	privacyPolicyURL STRING null,
	redirectURIs STRING null,
	rememberDevice BOOLEAN,
	trustedApplication BOOLEAN
);

create table OAuth2ApplicationScopeAliases (
	oA2AScopeAliasesId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	oAuth2ApplicationId LONG
);

create table OAuth2Authorization (
	oAuth2AuthorizationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	oAuth2ApplicationId LONG,
	oA2AScopeAliasesId LONG,
	accessTokenContent TEXT null,
	accessTokenContentHash LONG,
	accessTokenCreateDate DATE null,
	accessTokenExpirationDate DATE null,
	remoteHostInfo VARCHAR(255) null,
	remoteIPInfo VARCHAR(75) null,
	refreshTokenContent TEXT null,
	refreshTokenContentHash LONG,
	refreshTokenCreateDate DATE null,
	refreshTokenExpirationDate DATE null,
	rememberDeviceContent VARCHAR(75) null
);

create table OAuth2ScopeGrant (
	oAuth2ScopeGrantId LONG not null primary key,
	companyId LONG,
	oA2AScopeAliasesId LONG,
	applicationName VARCHAR(255) null,
	bundleSymbolicName VARCHAR(255) null,
	scope VARCHAR(240) null,
	scopeAliases TEXT null
);