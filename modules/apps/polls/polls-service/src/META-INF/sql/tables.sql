create table PollsChoice (
	uuid_ VARCHAR(75) null,
	choiceId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null
);

create table PollsQuestion (
	uuid_ VARCHAR(75) null,
	questionId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	expirationDate DATE null,
	lastVoteDate DATE null,
	lastPublishDate DATE null
);

create table PollsVote (
	uuid_ VARCHAR(75) null,
	voteId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	choiceId LONG,
	voteDate DATE null,
	lastPublishDate DATE null
);