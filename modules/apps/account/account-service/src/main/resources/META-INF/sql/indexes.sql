create unique index IX_FBFAF640 on AccountEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_48CB043 on AccountEntry (companyId, status);
create index IX_908C3410 on AccountEntry (userId, type_[$COLUMN_LENGTH:75$]);
create index IX_6901A669 on AccountEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B233281D on AccountEntryOrganizationRel (organizationId, accountEntryId);

create index IX_EED84268 on AccountEntryUserRel (accountUserId, accountEntryId);

create index IX_38BDB33 on AccountGroup (companyId, defaultAccountGroup);
create unique index IX_F7BFA1CD on AccountGroup (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_8EE6A92F on AccountGroup (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_B4733E65 on AccountGroup (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_E86A36FC on AccountGroup (uuid_[$COLUMN_LENGTH:75$]);

create index IX_617C42A3 on AccountGroupRel (classNameId, classPK, accountGroupId);

create index IX_9BCBCB2B on AccountRole (accountEntryId, companyId);
create index IX_714A358E on AccountRole (roleId);