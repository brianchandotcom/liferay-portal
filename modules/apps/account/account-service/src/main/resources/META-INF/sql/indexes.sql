create index IX_48CB043 on AccountEntry (companyId, status);

create index IX_EC6CC41D on AccountEntryOrganizationRel (accountEntryId, organizationId);

create index IX_ED720A80 on AccountEntryUserRel (accountEntryId, accountUserId);
create index IX_4EA60AB4 on AccountEntryUserRel (accountUserId);

create index IX_3A47CDD on AccountRole (accountEntryId);
create index IX_76E515F on AccountRole (companyId);
create index IX_714A358E on AccountRole (roleId);