create index IX_4B14110F on Blueprint (companyId);
create index IX_2621ED37 on Blueprint (groupId, status);
create index IX_3BEA732D on Blueprint (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_6DDFE0EF on Blueprint (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C1E177CF on Element (companyId, type_);
create index IX_84D54BE7 on Element (groupId, status, type_);
create index IX_972E318D on Element (groupId, type_);
create index IX_87FF1116 on Element (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F724E518 on Element (uuid_[$COLUMN_LENGTH:75$], groupId);