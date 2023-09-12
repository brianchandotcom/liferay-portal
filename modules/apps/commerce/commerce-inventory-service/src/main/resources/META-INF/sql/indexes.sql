create index IX_45C5C370 on CIAudit (companyId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_E7D143D9 on CIAudit (createDate);
create index IX_57638B52 on CIAudit (sku[$COLUMN_LENGTH:75$]);

create index IX_625D8A54 on CIBookedQuantity (companyId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_33BF9CB0 on CIBookedQuantity (expirationDate);
create index IX_EC1719EE on CIBookedQuantity (sku[$COLUMN_LENGTH:75$]);

create index IX_E160DC29 on CIReplenishmentItem (availabilityDate, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_967CACA8 on CIReplenishmentItem (commerceInventoryWarehouseId);
create index IX_C4760B6E on CIReplenishmentItem (companyId, availabilityDate);
create index IX_FD9E7E02 on CIReplenishmentItem (companyId, commerceInventoryWarehouseId);
create unique index IX_3462AACC on CIReplenishmentItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_62C4534C on CIReplenishmentItem (companyId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_6C8E22D3 on CIReplenishmentItem (sku[$COLUMN_LENGTH:75$], availabilityDate);
create index IX_F48BBEAB on CIReplenishmentItem (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_8427A055 on CIWarehouse (active_, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_331A3FD3 on CIWarehouse (companyId, active_, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_DADA8974 on CIWarehouse (companyId, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create unique index IX_68E6B8D8 on CIWarehouse (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7202EB2 on CIWarehouse (countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_8D2564B7 on CIWarehouse (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_3996C237 on CIWarehouseGroupRel (groupId, commerceWarehouseId, primary_);
create index IX_E4C1F39E on CIWarehouseGroupRel (groupId, primary_);

create unique index IX_B4413476 on CIWarehouseItem (commerceInventoryWarehouseId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create unique index IX_8A09C40B on CIWarehouseItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_B86B6C8B on CIWarehouseItem (companyId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_97CADC57 on CIWarehouseItem (sku[$COLUMN_LENGTH:75$]);
create index IX_A83335EA on CIWarehouseItem (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_E54A5A46 on CIWarehouseRel (CIWarehouseId);
create index IX_6C5212BC on CIWarehouseRel (classNameId, CIWarehouseId);
create unique index IX_988DE8D1 on CIWarehouseRel (classNameId, classPK, CIWarehouseId);