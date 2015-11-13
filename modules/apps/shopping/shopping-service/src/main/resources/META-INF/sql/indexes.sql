create unique index IX_FC46FE16 on ShoppingCart (groupId, userId);
create index IX_54101CC8 on ShoppingCart (userId);

create index IX_6A84467D on ShoppingCategory (groupId, name[$COLUMN_LENGTH:75$]);
create index IX_1E6464F5 on ShoppingCategory (groupId, parentCategoryId);

create unique index IX_DC60CFAE on ShoppingCoupon (code_[$COLUMN_LENGTH:75$]);
create index IX_3251AF16 on ShoppingCoupon (groupId);

create unique index IX_1C717CA6 on ShoppingItem (companyId, sku[$COLUMN_LENGTH:75$]);
create index IX_FEFE7D76 on ShoppingItem (groupId, categoryId);
create index IX_903DC750 on ShoppingItem (largeImageId);
create index IX_D217AB30 on ShoppingItem (mediumImageId);
create index IX_FF203304 on ShoppingItem (smallImageId);

create index IX_6D5F9B87 on ShoppingItemField (itemId);

create index IX_EA6FD516 on ShoppingItemPrice (itemId);

create index IX_119B5630 on ShoppingOrder (groupId, userId, ppPaymentStatus[$COLUMN_LENGTH:75$]);
create unique index IX_D7D6E87A on ShoppingOrder (number_[$COLUMN_LENGTH:75$]);
create index IX_F474FD89 on ShoppingOrder (ppTxnId[$COLUMN_LENGTH:75$]);

create index IX_B5F82C7A on ShoppingOrderItem (orderId);