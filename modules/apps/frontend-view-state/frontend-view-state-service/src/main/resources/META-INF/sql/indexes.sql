create unique index IX_FB8646EF on FrontendViewStateActiveEntry (userId, datasetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:200$]);
create index IX_F9A50D0C on FrontendViewStateActiveEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_B58CA5F2 on FrontendViewStateEntry (uuid_[$COLUMN_LENGTH:75$], companyId);