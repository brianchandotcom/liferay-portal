create unique index IX_726123B7 on CPDefinitionAvailabilityRange (CPDefinitionId);
create index IX_9032BB82 on CPDefinitionAvailabilityRange (groupId);
create index IX_58536E1C on CPDefinitionAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_64D8CB9E on CPDefinitionAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_34D62DF1 on CPDefinitionInventory (CPDefinitionId);
create index IX_51AED1D6 on CPDefinitionInventory (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_274DD5D8 on CPDefinitionInventory (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_7CC21117 on CPLQualificationTypeRel (CPriceListQualificationType[$COLUMN_LENGTH:75$], commercePriceListId);
create index IX_566A7482 on CPLQualificationTypeRel (commercePriceListId);
create index IX_F3C1E4E9 on CPLQualificationTypeRel (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_732091AB on CPLQualificationTypeRel (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_15EA4714 on CommerceAddress (classNameId, classPK);
create index IX_CD76FE87 on CommerceAddress (commerceCountryId);
create index IX_71C5A9DD on CommerceAddress (commerceRegionId);
create index IX_EEACF18E on CommerceAddress (groupId, classNameId, classPK, defaultBilling);
create index IX_333246DF on CommerceAddress (groupId, classNameId, classPK, defaultShipping);

create unique index IX_495311F8 on CommerceAddressRestriction (classNameId, classPK, commerceCountryId);
create index IX_69DBF5AD on CommerceAddressRestriction (commerceCountryId);

create index IX_DC0A8E5D on CommerceAvailabilityRange (groupId);
create index IX_A7FBA1A1 on CommerceAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_FD921C63 on CommerceAvailabilityRange (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_7BB13C80 on CommerceCountry (groupId, active_);
create index IX_FEDECABF on CommerceCountry (groupId, billingAllowed, active_);
create unique index IX_D84B0322 on CommerceCountry (groupId, numericISOCode);
create index IX_158112E8 on CommerceCountry (groupId, shippingAllowed, active_);
create index IX_91EA24D5 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7EFDC97 on CommerceCountry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_12131FC1 on CommerceOrder (billingAddressId);
create index IX_64C8D153 on CommerceOrder (groupId, orderUserId);
create index IX_67E0AF05 on CommerceOrder (groupId, userId, orderStatus);
create index IX_4B11FAD8 on CommerceOrder (shippingAddressId);
create index IX_5AF685CD on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_58101B8F on CommerceOrder (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_2E1BB39D on CommerceOrderItem (CPInstanceId);
create index IX_415AF3E3 on CommerceOrderItem (commerceOrderId, CPInstanceId);

create index IX_CEB86C22 on CommerceOrderNote (commerceOrderId, restricted);

create index IX_CF274005 on CommerceOrderPayment (commerceOrderId);

create index IX_1FB6FD31 on CommercePaymentMethod (groupId, active_);
create unique index IX_B0FDFD55 on CommercePaymentMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create unique index IX_2083879C on CommercePriceEntry (CPInstanceId, commercePriceListId);
create unique index IX_BD2945DE on CommercePriceEntry (commercePriceListId, CPInstanceId);
create index IX_5E36B51E on CommercePriceEntry (companyId);
create index IX_E185EB20 on CommercePriceEntry (groupId);
create index IX_1578F03E on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_F45C6E40 on CommercePriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_473B4D8D on CommercePriceList (commerceCurrencyId);
create index IX_2AA1AF56 on CommercePriceList (companyId);
create index IX_31913054 on CommercePriceList (displayDate, status);
create index IX_2C5B7A3E on CommercePriceList (groupId, status);
create index IX_FCE28706 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_554D1708 on CommercePriceList (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_49C93338 on CommerceRegion (commerceCountryId, active_);
create index IX_3BC85C89 on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DBA0714B on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_E829A2CF on CommerceShipment (groupId);

create index IX_9FE20732 on CommerceShipmentItem (commerceShipmentId);
create index IX_DB0BB83C on CommerceShipmentItem (groupId);

create index IX_42E5F6EF on CommerceShippingMethod (groupId, active_);
create unique index IX_C4557F93 on CommerceShippingMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create index IX_1143BF16 on CommerceTaxCategory (groupId);

create index IX_A8CAAE86 on CommerceTaxCategoryRel (classNameId, classPK);
create unique index IX_762A5C5D on CommerceTaxCategoryRel (commerceTaxCategoryId, classNameId, classPK);

create index IX_F3810116 on CommerceTaxMethod (groupId, active_);
create unique index IX_BA569BFA on CommerceTaxMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);

create unique index IX_A622C8AE on CommerceTierPriceEntry (commercePriceEntryId, minQuantity);
create index IX_F5D5725C on CommerceTierPriceEntry (companyId);
create index IX_D78EDFDE on CommerceTierPriceEntry (groupId);
create index IX_B6C47140 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_5D3847C2 on CommerceTierPriceEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_81487FD9 on CommerceWarehouse (groupId, active_, commerceCountryId);
create index IX_4500A0CA on CommerceWarehouse (groupId, commerceCountryId);

create index IX_B905F012 on CommerceWarehouseItem (CPInstanceId);
create unique index IX_8FBE7F43 on CommerceWarehouseItem (commerceWarehouseId, CPInstanceId);