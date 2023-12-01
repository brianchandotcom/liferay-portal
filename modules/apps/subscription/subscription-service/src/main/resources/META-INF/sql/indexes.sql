create index IX_6BBFF1A6 on Subscription (classNameId, companyId, classPK);
create index IX_C4FAEA47 on Subscription (groupId);
create unique index IX_6CA01A0A on Subscription (userId, classNameId, companyId, classPK, ctCollectionId);
create index IX_C717464D on Subscription (userId, groupId);