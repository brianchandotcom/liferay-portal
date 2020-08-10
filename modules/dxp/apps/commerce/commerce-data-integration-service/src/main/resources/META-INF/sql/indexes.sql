create unique index IX_90432679 on CDataIntegrationProcess (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_DEA66A5B on CDataIntegrationProcess (companyId, type_[$COLUMN_LENGTH:75$]);

create index IX_E34752B7 on CDataIntegrationProcessLog (CDataIntegrationProcessId, status);
create index IX_34A7284C on CDataIntegrationProcessLog (companyId, status);