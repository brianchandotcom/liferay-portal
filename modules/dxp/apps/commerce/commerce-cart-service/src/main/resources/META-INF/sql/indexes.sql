create index IX_D5C5B532 on CommerceCart (groupId, userId, type_);
create index IX_7CE14F4 on CommerceCart (type_);
create unique index IX_4C89541B on CommerceCart (userId, name[$COLUMN_LENGTH:75$], type_);

create index IX_8214F5DE on CommerceCartItem (CommerceCartId);