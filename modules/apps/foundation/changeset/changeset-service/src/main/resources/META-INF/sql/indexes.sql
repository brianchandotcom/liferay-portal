create index IX_2FA5EF0F on Changeset (companyId, name[$COLUMN_LENGTH:75$]);
create unique index IX_D91BA011 on Changeset (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_832BDC0C on Changeset (groupId, userId);

create unique index IX_599D446C on ChangesetEntry (changesetId, classNameId, classPK);
create index IX_CEB6AFA2 on ChangesetEntry (companyId);
create index IX_4A5B2D2A on ChangesetEntry (groupId, classNameId);