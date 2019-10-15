create table OAuth_OAuthApplication (
	oAuthApplicationId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	consumerKey VARCHAR(75) null,
	consumerSecret VARCHAR(75) null,
	accessLevel INTEGER,
	logoId LONG,
	shareableAccessToken BOOLEAN,
	callbackURI STRING null,
	websiteURL STRING null
);

create table OAuth_OAuthUser (
	oAuthUserId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	oAuthApplicationId LONG,
	accessToken VARCHAR(75) null,
	accessSecret VARCHAR(75) null
);