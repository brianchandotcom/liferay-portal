create index IX_2674F2A8 on TrashEntry (companyId);
create unique index IX_22690FB3 on TrashEntry (ctCollectionId, classNameId, classPK);
create unique index IX_E553C73 on TrashEntry (ctCollectionId, companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_FC4EEA64 on TrashEntry (groupId, classNameId);
create index IX_6CAAE2E8 on TrashEntry (groupId, createDate);
create unique index IX_EF9125A8 on TrashEntry (groupId, uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_C79A5834 on TrashEntry (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_96536499 on TrashVersion (classNameId, classPK, ctCollectionId);
create index IX_72D58D37 on TrashVersion (entryId, classNameId);