create table Subscription (
	mvccVersion LONG default 0 not null,
	ctCollectionId LONG default 0 not null,
	subscriptionId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	classNameId LONG,
	classPK LONG,
	frequency VARCHAR(75) null,
	primary key (subscriptionId, ctCollectionId)
);