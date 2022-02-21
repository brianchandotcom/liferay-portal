create unique index IX_DEFDE07C on CPLCommerceGroupAccountRel (commercePriceListId, commerceAccountGroupId);
create index IX_E475B7EB on CPLCommerceGroupAccountRel (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_2FCFB9FB on CommercePriceEntry (CPInstanceUuid[$COLUMN_LENGTH:75$]);
create index IX_E653D524 on CommercePriceEntry (commercePriceListId, CPInstanceUuid[$COLUMN_LENGTH:75$], status);
create index IX_B058565F on CommercePriceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_790F9C1C on CommercePriceEntry (displayDate, status);
create index IX_770DC1E1 on CommercePriceEntry (expirationDate, status);
create index IX_1578F03E on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_473B4D8D on CommercePriceList (commerceCurrencyId);
create index IX_328B5D27 on CommercePriceList (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_31913054 on CommercePriceList (displayDate, status);
create index IX_354C658C on CommercePriceList (groupId, catalogBasePriceList, type_[$COLUMN_LENGTH:75$]);
create index IX_B61658B6 on CommercePriceList (groupId, companyId, status);
create index IX_863045BB on CommercePriceList (parentCommercePriceListId);
create index IX_FCE28706 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_554D1708 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_3DEE5A98 on CommercePriceListAccountRel (commerceAccountId, commercePriceListId);
create index IX_7279F379 on CommercePriceListAccountRel (commercePriceListId);
create index IX_D598A152 on CommercePriceListAccountRel (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_2CCFD56C on CommercePriceListChannelRel (commerceChannelId, commercePriceListId);
create index IX_898B66CF on CommercePriceListChannelRel (commercePriceListId);
create index IX_F7DDDDBC on CommercePriceListChannelRel (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_CC15AA9A on CommercePriceListDiscountRel (commerceDiscountId, commercePriceListId);
create index IX_36D76E5 on CommercePriceListDiscountRel (commercePriceListId);
create index IX_96A5B566 on CommercePriceListDiscountRel (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_F4BEBD84 on CommercePriceListOrderTypeRel (commercePriceListId, commerceOrderTypeId);
create index IX_5A9B3277 on CommercePriceListOrderTypeRel (uuid_[$COLUMN_LENGTH:75$], companyId);

create unique index IX_A622C8AE on CommerceTierPriceEntry (commercePriceEntryId, minQuantity);
create index IX_95D59361 on CommerceTierPriceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_8A8963DA on CommerceTierPriceEntry (displayDate, status);
create index IX_21C0F963 on CommerceTierPriceEntry (expirationDate, status);
create index IX_B6C47140 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);