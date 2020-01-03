create index IX_9AC55E11 on ChangesetCollection (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_C806B2B5 on ChangesetCollection (groupId, name[$COLUMN_LENGTH:75$], companyId);
create index IX_EE4B4B0E on ChangesetCollection (groupId, userId);

create unique index IX_F301F4BE on ChangesetEntry (changesetCollectionId, classNameId, classPK, companyId);
create index IX_CEB6AFA2 on ChangesetEntry (companyId);
create index IX_4A5B2D2A on ChangesetEntry (groupId, classNameId);