create table SharingEntry (
	uuid_ VARCHAR(75) null,
	sharingEntryId LONG not null primary key,
	groupId LONG,
	userId LONG,
	companyId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	toUserId LONG,
	classNameId LONG,
	classPK LONG,
	shareable BOOLEAN,
	actionIds LONG,
	expirationDate DATE null
);