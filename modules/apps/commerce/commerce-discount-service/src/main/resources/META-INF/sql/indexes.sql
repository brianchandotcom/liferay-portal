create unique index IX_F11225F5 on CDiscountCAccountGroupRel (commerceAccountGroupId, commerceDiscountId);

create index IX_3CAC096A on CommerceDiscount (companyId, active_, couponCode[$COLUMN_LENGTH:75$]);
create index IX_5A1D8CDB on CommerceDiscount (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7F318E85 on CommerceDiscount (status, companyId, active_, levelType[$COLUMN_LENGTH:75$]);
create index IX_52CB3DB8 on CommerceDiscount (status, displayDate);
create index IX_DE0C3C39 on CommerceDiscount (status, expirationDate);
create index IX_F1A4C552 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_269AC6C4 on CommerceDiscountAccountRel (commerceDiscountId, commerceAccountId);
create index IX_CEE71686 on CommerceDiscountAccountRel (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_1F2F60F0 on CommerceDiscountOrderTypeRel (commerceOrderTypeId, commerceDiscountId);
create index IX_CEE22E81 on CommerceDiscountOrderTypeRel (uuid_[$COLUMN_LENGTH:75$]);

create index IX_6B4EEC38 on CommerceDiscountRel (classNameId, classPK);
create index IX_585D82B6 on CommerceDiscountRel (classNameId, commerceDiscountId);

create index IX_CB9E6769 on CommerceDiscountRule (commerceDiscountId);

create index IX_5527BBDD on CommerceDiscountUsageEntry (commerceDiscountId, commerceOrderId, commerceAccountId);