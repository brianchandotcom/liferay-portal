create index IX_9AC55E11 on ChangesetCollection (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_ABEEE793 on ChangesetCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_EE4B4B0E on ChangesetCollection (groupId, userId);

create unique index IX_EF48912A on ChangesetEntry (changesetCollectionId, classNameId, classPK);
create index IX_A9985762 on ChangesetEntry (classNameId, groupId);
create index IX_CEB6AFA2 on ChangesetEntry (companyId);
create index IX_E00AB6A4 on ChangesetEntry (groupId);