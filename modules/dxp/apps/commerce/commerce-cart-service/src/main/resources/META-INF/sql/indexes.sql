create index IX_13E7A9AC on CommerceCart (groupId, type_);
create index IX_280446D3 on CommerceCart (groupId, userId, name[$COLUMN_LENGTH:75$], type_);
create index IX_D5C5B532 on CommerceCart (groupId, userId, type_);

create index IX_8214F5DE on CommerceCartItem (CommerceCartId);