create table SharingEntry (
	uuid_ VARCHAR(75) null,
	sharingEntryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	fromUserId LONG,
	toUserId LONG,
	className VARCHAR(75) null,
	classPK LONG,
	actionIds LONG
);