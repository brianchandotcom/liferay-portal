create table CNTemplateCAccountGroupRel (
	mvccVersion LONG default 0 not null,
	CNTemplateCAccountGroupRelId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceNotificationTemplateId LONG,
	commerceAccountGroupId LONG
);

create table CNotificationAttachment (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	CNotificationAttachmentId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CNotificationQueueEntryId LONG,
	fileEntryId LONG,
	deleteOnSend BOOLEAN
);

create table CommerceNotificationQueueEntry (
	mvccVersion LONG default 0 not null,
	CNotificationQueueEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	commerceNotificationTemplateId LONG,
	from_ VARCHAR(75) null,
	fromName VARCHAR(75) null,
	to_ VARCHAR(75) null,
	toName VARCHAR(75) null,
	cc VARCHAR(255) null,
	bcc VARCHAR(255) null,
	subject VARCHAR(255) null,
	body TEXT null,
	priority DOUBLE,
	sent BOOLEAN,
	sentDate DATE null
);

create table CommerceNotificationTemplate (
	mvccVersion LONG default 0 not null,
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
	from_ TEXT null,
	fromName STRING null,
	to_ TEXT null,
	cc TEXT null,
	bcc TEXT null,
	type_ VARCHAR(75) null,
	enabled BOOLEAN,
	subject STRING null,
	body TEXT null
);