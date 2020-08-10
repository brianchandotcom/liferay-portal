create table CommerceSubscriptionEntry (
	uuid_ VARCHAR(75) null,
	commerceSubscriptionEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	CPInstanceId LONG,
	commerceOrderItemId LONG,
	subscriptionLength INTEGER,
	subscriptionType VARCHAR(75) null,
	subscriptionTypeSettings TEXT null,
	maxSubscriptionCycles LONG,
	subscriptionStatus INTEGER,
	lastIterationDate DATE null,
	nextIterationDate DATE null,
	startDate DATE null
);

create index IX_6D080A04 on CommerceSubscriptionEntry (groupId, userId);
create index IX_B496E103 on CommerceSubscriptionEntry (subscriptionStatus);
create index IX_4363DED4 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_943E0A56 on CommerceSubscriptionEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;