create index IX_53AE0C80 on CIAudit (companyId, sku[$COLUMN_LENGTH:75$]);
create index IX_E7D143D9 on CIAudit (createDate);
create index IX_57638B52 on CIAudit (sku[$COLUMN_LENGTH:75$]);

create index IX_1B168464 on CIBookedQuantity (companyId, sku[$COLUMN_LENGTH:75$]);
create index IX_33BF9CB0 on CIBookedQuantity (expirationDate);
create index IX_EC1719EE on CIBookedQuantity (sku[$COLUMN_LENGTH:75$]);

create index IX_F588314 on CIReplenishmentItem (availabilityDate);
create index IX_967CACA8 on CIReplenishmentItem (commerceInventoryWarehouseId);
create index IX_1B681B5C on CIReplenishmentItem (companyId, sku[$COLUMN_LENGTH:75$]);
create index IX_6C8E22D3 on CIReplenishmentItem (sku[$COLUMN_LENGTH:75$], availabilityDate);

create index IX_8427A055 on CIWarehouse (active_, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_331A3FD3 on CIWarehouse (companyId, active_, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_DADA8974 on CIWarehouse (companyId, countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create unique index IX_68E6B8D8 on CIWarehouse (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7202EB2 on CIWarehouse (countryTwoLettersISOCode[$COLUMN_LENGTH:75$]);
create index IX_8D2564B7 on CIWarehouse (uuid_[$COLUMN_LENGTH:75$], companyId);

create index IX_3996C237 on CIWarehouseGroupRel (groupId, commerceWarehouseId, primary_);
create index IX_E4C1F39E on CIWarehouseGroupRel (groupId, primary_);

create unique index IX_8D9FD306 on CIWarehouseItem (commerceInventoryWarehouseId, sku[$COLUMN_LENGTH:75$]);
create unique index IX_8A09C40B on CIWarehouseItem (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_F8B2565B on CIWarehouseItem (companyId, sku[$COLUMN_LENGTH:75$]);
create index IX_97CADC57 on CIWarehouseItem (sku[$COLUMN_LENGTH:75$]);
create index IX_A83335EA on CIWarehouseItem (uuid_[$COLUMN_LENGTH:75$], companyId);