create index IX_777290D8 on CommerceWishList (groupId, userId, defaultWishList);
create index IX_6680B6BE on CommerceWishList (userId, createDate);
create index IX_8F07123A on CommerceWishList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_2DC2DF3C on CommerceWishList (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6FB8DFC8 on CommerceWishListItem (CPDefinitionId);
create index IX_622C400A on CommerceWishListItem (CPInstanceId);
create index IX_14684288 on CommerceWishListItem (commerceWishListId);