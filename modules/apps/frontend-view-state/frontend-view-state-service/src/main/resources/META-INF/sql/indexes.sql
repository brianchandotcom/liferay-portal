create unique index IX_BDDBF1D7 on FVSActiveEntry (userId, clayDataSetDisplayId[$COLUMN_LENGTH:75$], plid, portletId[$COLUMN_LENGTH:200$]);
create index IX_DA57C1C5 on FVSActiveEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_762F36F0 on FVSCustomEntry (uuid_[$COLUMN_LENGTH:75$]);

create index IX_544B4F5F on FVSEntry (uuid_[$COLUMN_LENGTH:75$]);