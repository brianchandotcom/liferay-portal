create table IM_MemberRequest (
	memberRequestId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	key_ VARCHAR(75) null,
	receiverUserId LONG,
	invitedRoleId LONG,
	invitedTeamId LONG,
	status INTEGER,
	primary key (memberRequestId, companyId)
);