create table SamlIdpSpConnection (
	samlIdpSpConnectionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
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
	nameIdFormat VARCHAR(1024) null,
	samlSpEntityId VARCHAR(1024) null
);

create table SamlIdpSpSession (
	samlIdpSpSessionId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	samlIdpSsoSessionId LONG,
	samlPeerBindingId LONG
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

create table SamlPeerBinding (
	samlPeerBindingId LONG not null primary key,
	companyId LONG,
	createDate DATE null,
	userId LONG,
	userName VARCHAR(75) null,
	deleted BOOLEAN,
	samlNameIdFormat VARCHAR(75) null,
	samlNameIdNameQualifier VARCHAR(75) null,
	samlNameIdSpNameQualifier VARCHAR(75) null,
	samlNameIdSpProvidedId VARCHAR(75) null,
	samlNameIdValue VARCHAR(75) null,
	samlPeerEntityId VARCHAR(75) null
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
	assertionSignatureRequired BOOLEAN,
	clockSkew LONG,
	enabled BOOLEAN,
	forceAuthn BOOLEAN,
	ldapImportEnabled BOOLEAN,
	metadataUpdatedDate DATE null,
	metadataUrl VARCHAR(1024) null,
	metadataXml TEXT null,
	name VARCHAR(75) null,
	nameIdFormat VARCHAR(1024) null,
	samlIdpEntityId VARCHAR(1024) null,
	signAuthnRequest BOOLEAN,
	unknownUsersAreStrangers BOOLEAN,
	userAttributeMappings STRING null,
	userIdentifierExpression VARCHAR(200) null
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
	samlPeerBindingId LONG,
	assertionXml TEXT null,
	jSessionId VARCHAR(200) null,
	samlSpSessionKey VARCHAR(75) null,
	sessionIndex VARCHAR(75) null,
	terminated_ BOOLEAN
);