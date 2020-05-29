create unique index IX_16DA0033 on TrashEntry (classNameId, classPK, ctCollectionId);
create index IX_D7357906 on TrashEntry (companyId, ctCollectionId);
create index IX_FD0078C2 on TrashEntry (groupId, classNameId, ctCollectionId);
create index IX_7E02E946 on TrashEntry (groupId, createDate, ctCollectionId);
create index IX_D4D4588 on TrashEntry (groupId, ctCollectionId);

create unique index IX_96536499 on TrashVersion (classNameId, classPK, ctCollectionId);
create index IX_A2051595 on TrashVersion (entryId, classNameId, ctCollectionId);
create index IX_B0B4DD5 on TrashVersion (entryId, ctCollectionId);