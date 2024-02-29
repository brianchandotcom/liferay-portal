create index IX_777290D8 on CommerceWishList (groupId, userId, defaultWishList);
create unique index IX_FB7BEB90 on CommerceWishList (groupId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_6680B6BE on CommerceWishList (userId, createDate);
create index IX_47CF092E on CommerceWishList (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B8A10AD4 on CommerceWishListItem (CPInstanceUuid[$COLUMN_LENGTH:75$], CProductId, commerceWishListId);