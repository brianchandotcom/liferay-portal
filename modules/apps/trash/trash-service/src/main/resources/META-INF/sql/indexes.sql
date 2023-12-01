create unique index IX_16DA0033 on TrashEntry (classNameId, classPK, ctCollectionId);
create index IX_2674F2A8 on TrashEntry (companyId);
create index IX_FC4EEA64 on TrashEntry (groupId, classNameId);
create index IX_6CAAE2E8 on TrashEntry (groupId, createDate);

create unique index IX_96536499 on TrashVersion (classNameId, classPK, ctCollectionId);
create index IX_4708E575 on TrashVersion (classNameId, entryId);