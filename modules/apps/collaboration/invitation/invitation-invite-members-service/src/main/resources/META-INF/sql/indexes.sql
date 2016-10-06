create index IX_4C831DF9 on IM_MemberRequest (groupId, receiverUserId, status);
create index IX_B4BCD9B4 on IM_MemberRequest (key_[$COLUMN_LENGTH:75$]);
create index IX_B312EB0F on IM_MemberRequest (receiverUserId, status);

create index IX_D34593C1 on SO_MemberRequest (groupId, receiverUserId, status);
create index IX_212FA0EC on SO_MemberRequest (key_[$COLUMN_LENGTH:75$]);
create index IX_16475447 on SO_MemberRequest (receiverUserId, status);