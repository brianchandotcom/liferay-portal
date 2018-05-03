create index IX_9AC55E11 on ChangesetCollection (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_ABEEE793 on ChangesetCollection (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_EE4B4B0E on ChangesetCollection (groupId, userId);

create unique index IX_EF48912A on ChangesetEntry (changesetCollectionId, classNameId, classPK);
create index IX_CEB6AFA2 on ChangesetEntry (companyId);
create index IX_4A5B2D2A on ChangesetEntry (groupId, classNameId);