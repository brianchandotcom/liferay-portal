create table SharepointOAuth2TokenEntry (
	sharepointOAuth2TokenEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	accessToken TEXT null,
	configurationPid VARCHAR(75) null,
	expirationDate DATE null,
	refreshToken TEXT null
);