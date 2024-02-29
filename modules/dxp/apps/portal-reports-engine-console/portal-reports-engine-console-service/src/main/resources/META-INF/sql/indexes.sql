create index IX_7F79EB6F on Reports_Definition (companyId);
create unique index IX_A29C8EE3 on Reports_Definition (groupId, uuid_[$COLUMN_LENGTH:75$]);

create index IX_C9381DA7 on Reports_Source (companyId);
create unique index IX_717CFFAB on Reports_Source (groupId, uuid_[$COLUMN_LENGTH:75$]);