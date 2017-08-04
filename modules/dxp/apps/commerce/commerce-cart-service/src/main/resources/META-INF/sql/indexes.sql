create index IX_13E7A9AC on CommerceCart (groupId, type_);
create index IX_280446D3 on CommerceCart (groupId, userId, name[$COLUMN_LENGTH:75$], type_);
create index IX_BC3AFD75 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_73DE5D37 on CommerceCart (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_1F9EE843 on CommerceCartItem (CPDefinitionId);
create index IX_8F602B45 on CommerceCartItem (CPInstanceId);
create index IX_B450B1BE on CommerceCartItem (commerceCartId);