create table Contacts_Entry (
	entryId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	fullName VARCHAR(75) null,
	emailAddress VARCHAR(254) null,
	comments STRING null,
	primary key (entryId, companyId)
);