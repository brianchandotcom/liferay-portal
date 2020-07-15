create index IX_80A7978 on Subscription (companyId, classNameId, classPK, ctCollectionId);
create unique index IX_FCCD4132 on Subscription (companyId, userId, classNameId, classPK, ctCollectionId);
create index IX_CA6E52A5 on Subscription (groupId, ctCollectionId);
create index IX_16B6BFDF on Subscription (groupId, userId, ctCollectionId);
create index IX_F4B715CF on Subscription (userId, classNameId, ctCollectionId);
create index IX_55BB775B on Subscription (userId, ctCollectionId);