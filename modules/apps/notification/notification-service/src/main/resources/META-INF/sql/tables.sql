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
	body TEXT null,
	classNameId LONG,
	classPK LONG,
	priority DOUBLE,
	sentDate DATE null,
	subject TEXT null,
	type_ VARCHAR(75) null,
	status INTEGER
);

create table NotificationRecipient (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	notificationRecipientId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG
);

create table NotificationRecipientSetting (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	notificationRecipientSettingId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	notificationRecipientId LONG,
	name VARCHAR(75) null,
	value STRING null
);

create table NotificationTemplate (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	externalReferenceCode VARCHAR(75) null,
	notificationTemplateId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	objectDefinitionId LONG,
	body TEXT null,
	description VARCHAR(255) null,
	editorType VARCHAR(75) null,
	name STRING null,
	recipientType VARCHAR(75) null,
	subject STRING null,
	type_ VARCHAR(255) null
);