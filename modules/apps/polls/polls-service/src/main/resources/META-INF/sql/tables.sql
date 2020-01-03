create table PollsChoice (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	choiceId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	name VARCHAR(75) null,
	description STRING null,
	lastPublishDate DATE null,
	primary key (choiceId, companyId)
);

create table PollsQuestion (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	questionId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	title STRING null,
	description STRING null,
	expirationDate DATE null,
	lastPublishDate DATE null,
	lastVoteDate DATE null,
	primary key (questionId, companyId)
);

create table PollsVote (
	mvccVersion LONG default 0 not null,
	uuid_ VARCHAR(75) null,
	voteId LONG not null,
	groupId LONG,
	companyId LONG not null,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	questionId LONG,
	choiceId LONG,
	lastPublishDate DATE null,
	voteDate DATE null,
	primary key (voteId, companyId)
);