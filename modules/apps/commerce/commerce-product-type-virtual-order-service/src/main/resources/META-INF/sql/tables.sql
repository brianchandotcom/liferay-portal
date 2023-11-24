create table CVirtualOrderItemFileEntry (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	cVirtualOrderItemFileEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceVirtualOrderItemId LONG,
	fileEntryId LONG,
	url VARCHAR(255) null,
	usages INTEGER,
	version VARCHAR(75) null
);

create table CommerceVirtualOrderItem (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	commerceVirtualOrderItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceOrderItemId LONG,
	activationStatus INTEGER,
	duration LONG,
	maxUsages INTEGER,
	active_ BOOLEAN,
	startDate DATE null,
	endDate DATE null
);