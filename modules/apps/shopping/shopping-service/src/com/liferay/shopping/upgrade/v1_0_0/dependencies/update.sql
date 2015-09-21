alter table ShoppingItemField add companyId LONG;

alter table ShoppingItemPrice add companyId LONG;

alter table ShoppingOrderItem add companyId LONG;

COMMIT_TRANSACTION;