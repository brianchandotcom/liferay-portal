create table WebhookEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	webhookEntryId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	active_ BOOLEAN,
	destinationName VARCHAR(75) null,
	destinationWebhookEventKeys VARCHAR(150) null,
	name VARCHAR(75) null,
	secret VARCHAR(75) null,
	url VARCHAR(255) null
);