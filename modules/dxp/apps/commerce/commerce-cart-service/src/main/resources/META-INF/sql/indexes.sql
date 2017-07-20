create index IX_D5C5B532 on CommerceCart (groupId, userId, type_);
create index IX_7CE14F4 on CommerceCart (type_);
create unique index IX_4C89541B on CommerceCart (userId, name[$COLUMN_LENGTH:75$], type_);
create index IX_BC3AFD75 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_73DE5D37 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_8214F5DE on CommerceCartItem (CommerceCartId);
create index IX_AD323FA8 on CommerceCartItem (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1216902A on CommerceCartItem (uuid_[$COLUMN_LENGTH:75$], groupId);