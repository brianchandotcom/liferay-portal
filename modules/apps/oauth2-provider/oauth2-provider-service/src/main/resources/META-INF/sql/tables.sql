create table OAuthTwo_Application (
	id_ LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(75) null
);

create table OAuthTwo_Client (
	clientId VARCHAR(75) not null primary key,
	applicationPK LONG,
	clientSecret VARCHAR(75) null
);

create table OAuthTwo_RefreshToken (
	value VARCHAR(75) not null primary key,
	clientId VARCHAR(75) null,
	issuedAt LONG,
	lifetime LONG
);