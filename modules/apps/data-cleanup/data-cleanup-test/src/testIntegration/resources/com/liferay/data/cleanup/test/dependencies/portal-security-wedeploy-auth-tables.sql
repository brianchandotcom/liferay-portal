create table WeDeployAuth_WeDeployAuthApp (
	weDeployAuthAppId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	redirectURI VARCHAR(75) null,
	clientId VARCHAR(75) null,
	clientSecret VARCHAR(75) null
);

create table WeDeployAuth_WeDeployAuthToken (
	weDeployAuthTokenId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	clientId VARCHAR(75) null,
	token VARCHAR(75) null,
	type_ INTEGER
);