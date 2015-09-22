create table MeetupsEntry (
	meetupsEntryId LONG not null primary key,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title VARCHAR(75) null,
	description VARCHAR(75) null,
	startDate DATE null,
	endDate DATE null,
	totalAttendees INTEGER,
	maxAttendees INTEGER,
	price DOUBLE,
	thumbnailId LONG,
	companyId LONG
);

create table MeetupsRegistration (
	meetupsRegistrationId LONG not null primary key,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	meetupsEntryId LONG,
	status INTEGER,
	comments VARCHAR(75) null,
	companyId LONG
);

create table WallEntry (
	wallEntryId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	comments VARCHAR(75) null,
	companyId LONG
);