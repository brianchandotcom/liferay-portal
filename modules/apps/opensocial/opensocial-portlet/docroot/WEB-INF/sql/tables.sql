create table OpenSocial_Gadget (
	uuid_ VARCHAR(75) null,
	gadgetId LONG not null,
	companyId LONG not null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	url STRING null,
	portletCategoryNames STRING null,
	lastPublishDate DATE null,
	primary key (gadgetId, companyId)
);

create table OpenSocial_OAuthConsumer (
	oAuthConsumerId LONG not null,
	companyId LONG not null,
	createDate DATE null,
	modifiedDate DATE null,
	gadgetKey VARCHAR(75) null,
	serviceName VARCHAR(75) null,
	consumerKey VARCHAR(75) null,
	consumerSecret TEXT null,
	keyType VARCHAR(75) null,
	primary key (oAuthConsumerId, companyId)
);

create table OpenSocial_OAuthToken (
	oAuthTokenId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	gadgetKey VARCHAR(75) null,
	serviceName VARCHAR(75) null,
	moduleId LONG,
	accessToken VARCHAR(75) null,
	tokenName VARCHAR(75) null,
	tokenSecret VARCHAR(75) null,
	sessionHandle VARCHAR(75) null,
	expiration LONG,
	primary key (oAuthTokenId, companyId)
);