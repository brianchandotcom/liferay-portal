create table SharepointOAuth2TokenEntry (
	sharepointOAuth2TokenEntryId LONG not null,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	accessToken TEXT null,
	configurationPid VARCHAR(75) null,
	expirationDate DATE null,
	refreshToken TEXT null,
	primary key (sharepointOAuth2TokenEntryId, companyId)
);