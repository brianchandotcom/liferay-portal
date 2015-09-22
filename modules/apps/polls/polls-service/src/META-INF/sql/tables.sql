create table PollsChoice (
	uuid_ VARCHAR(75) null,
	choiceId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null,
	companyId LONG
);

create table PollsQuestion (
	uuid_ VARCHAR(75) null,
	questionId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	lastVoteDate DATE null,
	companyId LONG
);

create table PollsVote (
	uuid_ VARCHAR(75) null,
	voteId LONG not null primary key,
	groupId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	choiceId LONG,
	lastPublishDate DATE null,
	voteDate DATE null,
	companyId LONG
);