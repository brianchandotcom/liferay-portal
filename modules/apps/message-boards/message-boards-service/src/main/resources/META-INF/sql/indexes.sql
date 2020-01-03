create index IX_69951A25 on MBBan (banUserId);
create unique index IX_6B92130D on MBBan (groupId, banUserId, companyId);
create index IX_48814BBA on MBBan (userId);
create index IX_4F841574 on MBBan (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_20428572 on MBBan (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_D1642361 on MBCategory (categoryId, groupId, parentCategoryId, status);
create index IX_E15A5DB5 on MBCategory (companyId, status);
create index IX_C295DBEE on MBCategory (groupId, parentCategoryId, status);
create index IX_DA84A9F7 on MBCategory (groupId, status);
create index IX_13DF4E6D on MBCategory (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BE755499 on MBCategory (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_779A92F0 on MBDiscussion (classNameId, classPK, companyId);
create unique index IX_67EE3FCC on MBDiscussion (threadId, companyId);
create index IX_7E965757 on MBDiscussion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_848B2EF on MBDiscussion (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_BFEB984F on MBMailingList (active_);
create unique index IX_9F0DAB2B on MBMailingList (groupId, categoryId, companyId);
create index IX_FC61676E on MBMailingList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6888CCB8 on MBMailingList (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_F6687633 on MBMessage (classNameId, classPK, status);
create index IX_1AD93C16 on MBMessage (companyId, status);
create index IX_4257DB85 on MBMessage (groupId, categoryId, status);
create index IX_CBFDBF0A on MBMessage (groupId, categoryId, threadId, answer);
create index IX_385E123E on MBMessage (groupId, categoryId, threadId, status);
create index IX_ED39AC98 on MBMessage (groupId, status);
create index IX_377858D2 on MBMessage (groupId, userId, status);
create index IX_6A095F16 on MBMessage (parentMessageId, status);
create index IX_9D7C3B23 on MBMessage (threadId, answer);
create index IX_A7038CD7 on MBMessage (threadId, parentMessageId);
create index IX_9DC8E57 on MBMessage (threadId, status);
create index IX_4A4BB4ED on MBMessage (userId, classNameId, classPK, status);
create index IX_3321F142 on MBMessage (userId, classNameId, status);
create index IX_57CA9FEC on MBMessage (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_202441FA on MBMessage (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create unique index IX_B98AB9BF on MBStatsUser (groupId, userId, companyId);
create index IX_D33A5445 on MBStatsUser (groupId, userId, messageCount);
create index IX_847F92B5 on MBStatsUser (userId);

create index IX_41F6DC8A on MBThread (categoryId, priority);
create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate);
create index IX_485F7E98 on MBThread (groupId, categoryId, status);
create index IX_E1E7142B on MBThread (groupId, status);
create index IX_AEDD9CB5 on MBThread (lastPostDate, priority);
create index IX_CC993ECB on MBThread (rootMessageId);
create index IX_F8CA2AB9 on MBThread (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_63CF8DCD on MBThread (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);

create index IX_8CB0A24A on MBThreadFlag (threadId);
create unique index IX_C6EEE0A4 on MBThreadFlag (userId, threadId, companyId);
create index IX_DCE308C5 on MBThreadFlag (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BE41BD41 on MBThreadFlag (uuid_[$COLUMN_LENGTH:75$], groupId, companyId);