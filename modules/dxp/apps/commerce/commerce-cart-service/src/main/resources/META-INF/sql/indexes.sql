create index IX_13E7A9AC on CommerceCart (groupId, type_);
create unique index IX_280446D3 on CommerceCart (groupId, userId, name[$COLUMN_LENGTH:75$], type_);
create unique index IX_2F7BD771 on CommerceCart (groupId, userId, type_, name[$COLUMN_LENGTH:75$]);

create index IX_B450B1BE on CommerceCartItem (commerceCartId);