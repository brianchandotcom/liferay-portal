create unique index IX_BDDBF1D7 on FVSActiveEntry (userId, clayDataSetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:200$]);
create index IX_D023E543 on FVSActiveEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_5D25DC8F on FVSCustomEntry (userId, entityId[$COLUMN_LENGTH:75$], name[$COLUMN_LENGTH:75$]);
create index IX_B3581738 on FVSCustomEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_2943B369 on FVSEntry (uuid_[$COLUMN_LENGTH:75$], companyId);