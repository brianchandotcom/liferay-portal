create table SiteFriendlyURL (
	uuid_ VARCHAR(75) null,
	siteFriendlyURLId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	groupId LONG,
	friendlyURL VARCHAR(255) null,
	languageId VARCHAR(75) null,
	lastPublishDate DATE null
);