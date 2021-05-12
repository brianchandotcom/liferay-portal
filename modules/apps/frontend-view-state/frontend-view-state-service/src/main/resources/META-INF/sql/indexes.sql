create unique index IX_929B4A98 on FVSActiveEntry (userId, datasetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:75$]);
create index IX_D023E543 on FVSActiveEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_2943B369 on FVSEntry (uuid_[$COLUMN_LENGTH:75$], companyId);