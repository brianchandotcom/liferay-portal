create index IX_3A8B8AC on CCart (type_);
create index IX_7707C432 on CCart (userId, type_);
create index IX_581B56BD on CCart (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_8514687F on CCart (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_28E73B2 on CCartItem (CCartId);
create index IX_4334B4F0 on CCartItem (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3745B772 on CCartItem (uuid_[$COLUMN_LENGTH:75$], groupId);