create index IX_69951A25 on MBBan (banUserId);
create unique index IX_80F14E99 on MBBan (groupId, banUserId, ctCollectionId);
create unique index IX_9F0D6D2C on MBBan (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_48814BBA on MBBan (userId);

create index IX_BC735DCF on MBCategory (companyId);
create unique index IX_B73EC7E5 on MBCategory (groupId, ctCollectionId, friendlyURL[$COLUMN_LENGTH:255$]);
create index IX_72DC3FF5 on MBCategory (groupId, parentCategoryId, categoryId);
create index IX_850D075B on MBCategory (groupId, status, parentCategoryId, categoryId);
create unique index IX_7B308E1 on MBCategory (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_AB585C29 on MBCategory (status, companyId);
create index IX_C2626EDB on MBCategory (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_4B5416 on MBDiscussion (ctCollectionId, classNameId, classPK);
create unique index IX_C88E75BA on MBDiscussion (ctCollectionId, threadId);
create unique index IX_85E7BF7 on MBDiscussion (ctCollectionId, uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_BFEB984F on MBMailingList (active_);
create unique index IX_5AFAF63B on MBMailingList (groupId, ctCollectionId, categoryId);
create unique index IX_320EC1A6 on MBMailingList (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);

create index IX_51A8D44D on MBMessage (classNameId, classPK);
create index IX_B1432D30 on MBMessage (companyId);
create index IX_1073AB9F on MBMessage (groupId, categoryId);
create unique index IX_CAD6292D on MBMessage (groupId, ctCollectionId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_C3FB4E01 on MBMessage (groupId, ctCollectionId, urlSubject[$COLUMN_LENGTH:255$]);
create unique index IX_1AF445A4 on MBMessage (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_1D0DEF85 on MBMessage (groupId, status, categoryId);
create index IX_5084DE7E on MBMessage (groupId, status, threadId, categoryId);
create index IX_D12CECD2 on MBMessage (groupId, status, userId);
create index IX_C19015CA on MBMessage (groupId, threadId, categoryId, answer);
create index IX_8EB8C5EC on MBMessage (groupId, userId);
create index IX_58465030 on MBMessage (parentMessageId);
create index IX_936ECAB3 on MBMessage (status, classNameId, classPK);
create index IX_E4D73A8A on MBMessage (status, companyId);
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
create unique index IX_A3E15B5B on MBSuspiciousActivity (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_41F6DC8A on MBThread (categoryId, priority);
create index IX_50F1904A on MBThread (groupId, categoryId, lastPostDate);
create index IX_485F7E98 on MBThread (groupId, categoryId, status);
create index IX_E1E7142B on MBThread (groupId, status);
create unique index IX_4790702D on MBThread (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_15AE30B5 on MBThread (priority, lastPostDate);
create index IX_CC993ECB on MBThread (rootMessageId);
create index IX_7E264A0F on MBThread (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_E6878962 on MBThreadFlag (ctCollectionId, threadId, userId);
create unique index IX_C26914E5 on MBThreadFlag (ctCollectionId, uuid_[$COLUMN_LENGTH:75$], groupId);