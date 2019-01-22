create index IX_4410586B on Subscription (classNameId);
create index IX_786D171A on Subscription (companyId, classNameId, classPK);
create unique index IX_2E1A92D4 on Subscription (companyId, userId, classNameId, classPK);
create index IX_1290B81 on Subscription (groupId, userId);
create index IX_E8F34171 on Subscription (userId, classNameId);