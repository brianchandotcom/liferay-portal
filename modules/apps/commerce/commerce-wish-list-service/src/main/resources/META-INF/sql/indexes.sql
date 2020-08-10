create index IX_777290D8 on CommerceWishList (groupId, userId, defaultWishList);
create index IX_6680B6BE on CommerceWishList (userId, createDate);
create index IX_8F07123A on CommerceWishList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_2DC2DF3C on CommerceWishList (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_9DA3D36A on CommerceWishListItem (CPInstanceUuid[$COLUMN_LENGTH:75$]);
create index IX_CF9B9CD4 on CommerceWishListItem (CProductId);
create index IX_BC95AC54 on CommerceWishListItem (commerceWishListId, CPInstanceUuid[$COLUMN_LENGTH:75$], CProductId);
create index IX_C172BCA3 on CommerceWishListItem (commerceWishListId, CProductId);