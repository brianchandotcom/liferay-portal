create table CommerceWishList (
	mvccVersion LONG default 0 not null,
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
	mvccVersion LONG default 0 not null,
	commerceWishListItemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commerceWishListId LONG,
	CPInstanceUuid VARCHAR(75) null,
	CProductId LONG,
	json TEXT null
);