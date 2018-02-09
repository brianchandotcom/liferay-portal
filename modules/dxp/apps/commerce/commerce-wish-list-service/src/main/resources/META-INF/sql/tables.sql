create table CommerceWishList (
	uuid_ VARCHAR(75) null,
	commerceWishListId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	defaultWishList BOOLEAN
);

create table CommerceWishListItem (
	commerceWishListItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceWishListId LONG,
	CPDefinitionId LONG,
	CPInstanceId LONG,
	json TEXT null
);