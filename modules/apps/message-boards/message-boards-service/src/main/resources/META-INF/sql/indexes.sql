create unique index IX_863AE083 on MBBan (banUserId, groupId, ctCollectionId);
create index IX_48814BBA on MBBan (userId);
create index IX_8A13C634 on MBBan (uuid_[$COLUMN_LENGTH:75$]);

create index IX_BC735DCF on MBCategory (companyId);
create unique index IX_1C6A43E1 on MBCategory (groupId, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_72DC3FF5 on MBCategory (groupId, parentCategoryId, categoryId);
create index IX_850D075B on MBCategory (groupId, status, parentCategoryId, categoryId);
create index IX_AB585C29 on MBCategory (status, companyId);
create index IX_C2626EDB on MBCategory (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4B5416 on MBDiscussion (ctCollectionId, classNameId, classPK);
create unique index IX_C88E75BA on MBDiscussion (ctCollectionId, threadId);
create index IX_5477D431 on MBDiscussion (uuid_[$COLUMN_LENGTH:75$]);

create index IX_BFEB984F on MBMailingList (active_);
create unique index IX_D626193B on MBMailingList (groupId, categoryId, ctCollectionId);
create index IX_4115EC7A on MBMailingList (uuid_[$COLUMN_LENGTH:75$]);

create index IX_51A8D44D on MBMessage (classNameId, classPK);
create index IX_B1432D30 on MBMessage (companyId);
create index IX_E787CA45 on MBMessage (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_1073AB9F on MBMessage (groupId, categoryId);
create index IX_C19015CA on MBMessage (groupId, threadId, categoryId, answer);
create unique index IX_8813E901 on MBMessage (groupId, urlSubject[$COLUMN_LENGTH:255$], ctCollectionId);
create index IX_8EB8C5EC on MBMessage (groupId, userId);
create index IX_58465030 on MBMessage (parentMessageId);
create index IX_936ECAB3 on MBMessage (status, classNameId, classPK);
create index IX_E4D73A8A on MBMessage (status, companyId);
create index IX_D0825079 on MBMessage (status, groupId, categoryId);
create index IX_EFFB9E72 on MBMessage (status, groupId, threadId, categoryId);
create index IX_D3FE7C6 on MBMessage (status, groupId, userId);
create index IX_D6CD720A on MBMessage (status, parentMessageId);
create index IX_6F212FD7 on MBMessage (status, threadId);
create index IX_2E9F9D6D on MBMessage (status, userId, classNameId, classPK);
create index IX_9D7C3B23 on MBMessage (threadId, answer);
create index IX_A7038CD7 on MBMessage (threadId, parentMessageId);
create index IX_ABEB6D07 on MBMessage (userId, classNameId, classPK);
create index IX_C57B16BC on MBMessage (uuid_[$COLUMN_LENGTH:75$]);

create index IX_977DB0CB on MBSuspiciousActivity (messageId);
create index IX_9EE25540 on MBSuspiciousActivity (threadId);
create index IX_39C9A751 on MBSuspiciousActivity (userId, messageId);
create index IX_939A75FA on MBSuspiciousActivity (userId, threadId);
create index IX_C461DC4D on MBSuspiciousActivity (uuid_[$COLUMN_LENGTH:75$]);

create index IX_41F6DC8A on MBThread (categoryId, priority);
create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate);
create index IX_485F7E98 on MBThread (groupId, categoryId, status);
create index IX_E1E7142B on MBThread (groupId, status);
create index IX_15AE30B5 on MBThread (priority, lastPostDate);
create index IX_CC993ECB on MBThread (rootMessageId);
create index IX_7E264A0F on MBThread (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_93555EE2 on MBThreadFlag (threadId, userId, ctCollectionId);
create index IX_F36BBB83 on MBThreadFlag (uuid_[$COLUMN_LENGTH:75$]);