create table SharingEntry (
	uuid_ VARCHAR(75) null,
	sharingEntryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	toUserId LONG,
	classNameId LONG,
	classPK LONG,
	shareable BOOLEAN,
	actionIds LONG,
	expirationDate DATE null,
	primary key (sharingEntryId, companyId)
);