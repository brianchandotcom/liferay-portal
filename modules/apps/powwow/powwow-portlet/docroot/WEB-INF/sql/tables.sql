create table PowwowMeeting (
	powwowMeetingId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	powwowServerId LONG,
	name VARCHAR(75) null,
	description STRING null,
	providerType VARCHAR(75) null,
	providerTypeMetadata STRING null,
	languageId VARCHAR(75) null,
	calendarBookingId LONG,
	status INTEGER
);

create table PowwowParticipant (
	powwowParticipantId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	powwowMeetingId LONG,
	name VARCHAR(75) null,
	participantUserId LONG,
	emailAddress VARCHAR(75) null,
	type_ INTEGER,
	status INTEGER
);

create table PowwowServer (
	powwowServerId LONG not null primary key,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	providerType VARCHAR(75) null,
	url STRING null,
	apiKey VARCHAR(75) null,
	secret VARCHAR(75) null,
	active_ BOOLEAN
);