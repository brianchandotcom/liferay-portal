create index IX_98365DA4 on CommerceWishList (groupId);
create index IX_6680B6BE on CommerceWishList (userId, createDate);
create index IX_3CBFC78C on CommerceWishList (userId, groupId, defaultWishList);
create index IX_47CF092E on CommerceWishList (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B8A10AD4 on CommerceWishListItem (CPInstanceUuid[$COLUMN_LENGTH:75$], CProductId, commerceWishListId);