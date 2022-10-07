create table NQueueEntryAttachment (
	mvccVersion LONG default 0 not null,
	NQueueEntryAttachmentId LONG not null primary key,
	companyId LONG,
	fileEntryId LONG,
	notificationQueueEntryId LONG
);

create table NTemplateAttachment (
	mvccVersion LONG default 0 not null,
	NTemplateAttachmentId LONG not null primary key,
	companyId LONG,
	notificationTemplateId LONG,
	objectFieldId LONG
);

create table NotificationQueueEntry (
	mvccVersion LONG default 0 not null,
	notificationQueueEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	notificationTemplateId LONG,
	bcc VARCHAR(75) null,
	body TEXT null,
	cc VARCHAR(75) null,
	classNameId LONG,
	classPK LONG,
	from_ VARCHAR(75) null,
	fromName VARCHAR(75) null,
	priority DOUBLE,
	sentDate DATE null,
	subject VARCHAR(75) null,
	to_ VARCHAR(75) null,
	toName VARCHAR(75) null,
	type_ VARCHAR(75) null,
	status INTEGER
);

create table NotificationTemplate (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	notificationTemplateId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	objectDefinitionId LONG,
	bcc VARCHAR(75) null,
	body TEXT null,
	cc VARCHAR(75) null,
	description VARCHAR(75) null,
	from_ VARCHAR(75) null,
	fromName STRING null,
	name STRING null,
	recipientType VARCHAR(75) null,
	subject STRING null,
	to_ STRING null,
	type_ VARCHAR(75) null
);