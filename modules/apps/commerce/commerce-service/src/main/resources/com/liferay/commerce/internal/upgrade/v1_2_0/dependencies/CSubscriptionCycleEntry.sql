create table CSubscriptionCycleEntry (
	uuid_ VARCHAR(75) null,
	CSubscriptionCycleEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceSubscriptionEntryId LONG,
	commerceOrderItemId LONG,
	renew BOOLEAN
);

create unique index IX_B1B042BF on CSubscriptionCycleEntry (commerceOrderItemId);
create index IX_7A4C6193 on CSubscriptionCycleEntry (commerceSubscriptionEntryId);
create index IX_37D2CBD8 on CSubscriptionCycleEntry (groupId);
create index IX_EC715E86 on CSubscriptionCycleEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_E1ABCE88 on CSubscriptionCycleEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

COMMIT_TRANSACTION;