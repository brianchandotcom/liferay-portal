create unique index IX_8EF03EDA on CPLCommerceGroupAccountRel (commercePriceListId, commerceAccountGroupId, ctCollectionId);
create index IX_EE11009E on CPLCommerceGroupAccountRel (commercePriceListId, ctCollectionId);
create index IX_69815849 on CPLCommerceGroupAccountRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_7388047B on CPLCommerceGroupAccountRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_1F353A59 on CommercePriceEntry (CPInstanceUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_101D4E9C on CommercePriceEntry (commercePriceListId, CPInstanceUuid[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_2357E382 on CommercePriceEntry (commercePriceListId, CPInstanceUuid[$COLUMN_LENGTH:75$], status, ctCollectionId);
create index IX_208B496B on CommercePriceEntry (commercePriceListId, ctCollectionId);
create index IX_557F8F7C on CommercePriceEntry (companyId, ctCollectionId);
create index IX_95608EBD on CommercePriceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_6656BA7A on CommercePriceEntry (displayDate, status, ctCollectionId);
create index IX_4822B63F on CommercePriceEntry (expirationDate, status, ctCollectionId);
create index IX_84278A9C on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_896A0808 on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_8BFB69EB on CommercePriceList (commerceCurrencyId, ctCollectionId);
create index IX_95DF19B4 on CommercePriceList (companyId, ctCollectionId);
create index IX_517D0585 on CommercePriceList (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D658DEB2 on CommercePriceList (displayDate, status, ctCollectionId);
create index IX_C19D1887 on CommercePriceList (groupId, catalogBasePriceList, ctCollectionId);
create index IX_D432A3EA on CommercePriceList (groupId, catalogBasePriceList, type_[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_91B4202E on CommercePriceList (groupId, companyId, ctCollectionId);
create index IX_1C950314 on CommercePriceList (groupId, companyId, status, ctCollectionId);
create index IX_70864619 on CommercePriceList (parentCommercePriceListId, ctCollectionId);
create index IX_65A9164 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_27D76E40 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_78F8DD66 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create unique index IX_D44100F6 on CommercePriceListAccountRel (commerceAccountId, commercePriceListId, ctCollectionId);
create index IX_53E8B7D7 on CommercePriceListAccountRel (commercePriceListId, ctCollectionId);
create index IX_E60A93B0 on CommercePriceListAccountRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_3091E374 on CommercePriceListAccountRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_A19A53CA on CommercePriceListChannelRel (commerceChannelId, commercePriceListId, ctCollectionId);
create index IX_90C4BF2D on CommercePriceListChannelRel (commercePriceListId, ctCollectionId);
create index IX_14ADBC1A on CommercePriceListChannelRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_C913D94A on CommercePriceListChannelRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_EB1C0CF8 on CommercePriceListDiscountRel (commerceDiscountId, commercePriceListId, ctCollectionId);
create index IX_931FE343 on CommercePriceListDiscountRel (commercePriceListId, ctCollectionId);
create index IX_7FB4FFC4 on CommercePriceListDiscountRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_52B23BE0 on CommercePriceListDiscountRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create unique index IX_4EA60BE2 on CommercePriceListOrderTypeRel (commercePriceListId, commerceOrderTypeId, ctCollectionId);
create index IX_80768292 on CommercePriceListOrderTypeRel (commercePriceListId, ctCollectionId);
create index IX_6583AD5 on CommercePriceListOrderTypeRel (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_2AA5416F on CommercePriceListOrderTypeRel (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);

create index IX_4EACBBFD on CommerceTierPriceEntry (commercePriceEntryId, ctCollectionId);
create unique index IX_4072830C on CommerceTierPriceEntry (commercePriceEntryId, minQuantity, ctCollectionId);
create index IX_F329A7F2 on CommerceTierPriceEntry (commercePriceEntryId, minQuantity, status, ctCollectionId);
create index IX_E32810BA on CommerceTierPriceEntry (companyId, ctCollectionId);
create index IX_FC1787BF on CommerceTierPriceEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_4F754638 on CommerceTierPriceEntry (displayDate, status, ctCollectionId);
create index IX_A1CAA9C1 on CommerceTierPriceEntry (expirationDate, status, ctCollectionId);
create index IX_69A4C79E on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_2390D846 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);