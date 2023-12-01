create index IX_6D669D6F on SAPEntry (companyId, defaultSAPEntry);
create index IX_90740311 on SAPEntry (companyId, name[$COLUMN_LENGTH:75$]);
create index IX_DE62235E on SAPEntry (uuid_[$COLUMN_LENGTH:75$]);