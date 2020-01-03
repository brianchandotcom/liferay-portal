create unique index IX_DCD34933 on TrashEntry (classNameId, classPK, companyId);
create index IX_2674F2A8 on TrashEntry (companyId);
create index IX_FC4EEA64 on TrashEntry (groupId, classNameId);
create index IX_6CAAE2E8 on TrashEntry (groupId, createDate);

create unique index IX_3ABA3D0D on TrashVersion (classNameId, classPK, companyId);
create index IX_72D58D37 on TrashVersion (entryId, classNameId);