create table SamlIdpSpConnection (
	samlIdpSpConnectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlSpEntityId VARCHAR(1024) null,
	assertionLifetime INTEGER,
	attributeNames STRING null,
	attributesEnabled BOOLEAN,
	attributesNamespaceEnabled BOOLEAN,
	enabled BOOLEAN,
	encryptionForced BOOLEAN,
	metadataUrl VARCHAR(1024) null,
	metadataXml TEXT null,
	metadataUpdatedDate DATE null,
	name VARCHAR(75) null,
	nameIdAttribute VARCHAR(1024) null,
	nameIdFormat VARCHAR(1024) null
);

create table SamlIdpSpSession (
	samlIdpSpSessionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlIdpSsoSessionId LONG,
	samlSpEntityId VARCHAR(1024) null,
	nameIdFormat VARCHAR(1024) null,
	nameIdValue VARCHAR(1024) null
);

create table SamlIdpSsoSession (
	samlIdpSsoSessionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlIdpSsoSessionKey VARCHAR(75) null
);

create table SamlSpAuthRequest (
	samlSpAuthnRequestId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	samlIdpEntityId VARCHAR(1024) null,
	samlSpAuthRequestKey VARCHAR(75) null
);

create table SamlSpIdpConnection (
	samlSpIdpConnectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlIdpEntityId VARCHAR(1024) null,
	assertionSignatureRequired BOOLEAN,
	clockSkew LONG,
	enabled BOOLEAN,
	forceAuthn BOOLEAN,
	ldapImportEnabled BOOLEAN,
	unknownUsersAreStrangers BOOLEAN,
	metadataUrl VARCHAR(1024) null,
	metadataXml TEXT null,
	metadataUpdatedDate DATE null,
	name VARCHAR(75) null,
	nameIdFormat VARCHAR(1024) null,
	signAuthnRequest BOOLEAN,
	userAttributeMappings STRING null
);

create table SamlSpMessage (
	samlSpMessageId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	samlIdpEntityId VARCHAR(1024) null,
	samlIdpResponseKey VARCHAR(75) null,
	expirationDate DATE null
);

create table SamlSpSession (
	samlSpSessionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlIdpEntityId VARCHAR(1024) null,
	samlSpSessionKey VARCHAR(75) null,
	assertionXml TEXT null,
	jSessionId VARCHAR(200) null,
	nameIdFormat VARCHAR(1024) null,
	nameIdNameQualifier VARCHAR(1024) null,
	nameIdSPNameQualifier VARCHAR(1024) null,
	nameIdValue VARCHAR(1024) null,
	sessionIndex VARCHAR(75) null,
	terminated_ BOOLEAN
);