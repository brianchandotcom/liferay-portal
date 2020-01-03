create unique index IX_239A9F9 on PollsChoice (questionId, name[$COLUMN_LENGTH:75$], companyId);
create index IX_8AE746EF on PollsChoice (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_722FCE57 on PollsChoice (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_9FF342EA on PollsQuestion (groupId);
create index IX_F910BBB4 on PollsQuestion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_25ECE732 on PollsQuestion (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_D5DF7B54 on PollsVote (choiceId);
create index IX_1BBFD4D3 on PollsVote (questionId, userId);
create index IX_7D8E92B8 on PollsVote (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DD36B4AE on PollsVote (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);