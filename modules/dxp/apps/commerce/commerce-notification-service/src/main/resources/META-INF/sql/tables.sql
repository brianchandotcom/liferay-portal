create table CENTemplate (
	uuid_ VARCHAR(75) null,
	CENTemplateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	cc VARCHAR(75) null,
	ccn VARCHAR(75) null,
	type_ VARCHAR(75) null,
	target VARCHAR(75) null,
	enabled BOOLEAN,
	subject STRING null,
	body STRING null
);

create table CNTemplateUserSegmentRel (
	CNTemplateUserSegmentRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceNotificationTemplateId LONG,
	commerceUserSegmentEntryId LONG
);

create table CommerceNotificationTemplate (
	uuid_ VARCHAR(75) null,
	commerceNotificationTemplateId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description STRING null,
	cc VARCHAR(75) null,
	ccn VARCHAR(75) null,
	type_ VARCHAR(75) null,
	enabled BOOLEAN,
	subject STRING null,
	body TEXT null
);