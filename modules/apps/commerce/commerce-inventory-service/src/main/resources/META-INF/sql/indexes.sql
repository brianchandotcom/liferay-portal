create index IX_45C5C370 on CIAudit (companyId, sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$]);
create index IX_E7D143D9 on CIAudit (createDate);

create index IX_33BF9CB0 on CIBookedQuantity (expirationDate);
create index IX_EB8535EA on CIBookedQuantity (sku[$COLUMN_LENGTH:75$], companyId, unitOfMeasureKey[$COLUMN_LENGTH:75$]);

create index IX_F588314 on CIReplenishmentItem (availabilityDate);
create index IX_967CACA8 on CIReplenishmentItem (commerceInventoryWarehouseId);
create index IX_92B70AE6 on CIReplenishmentItem (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_DA9C2C43 on CIReplenishmentItem (sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$], availabilityDate);
create index IX_44CB5502 on CIReplenishmentItem (sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$], companyId);
create index IX_B359B95D on CIReplenishmentItem (uuid_[$COLUMN_LENGTH:75$]);

create index IX_C0564815 on CIWarehouse (companyId, countryTwoLettersISOCode[$COLUMN_LENGTH:75$], active_);
create index IX_CAAF4C5A on CIWarehouse (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_3CCB62D1 on CIWarehouse (uuid_[$COLUMN_LENGTH:75$]);

create index IX_8C9705F5 on CIWarehouseGroupRel (groupId, primary_, commerceWarehouseId);

create index IX_8BA34307 on CIWarehouseItem (externalReferenceCode[$COLUMN_LENGTH:75$]);
create unique index IX_10550FB8 on CIWarehouseItem (sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$], commerceInventoryWarehouseId);
create index IX_9A726E41 on CIWarehouseItem (sku[$COLUMN_LENGTH:75$], unitOfMeasureKey[$COLUMN_LENGTH:75$], companyId);
create index IX_4AD4537E on CIWarehouseItem (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_A743341B on CIWarehouseRel (CIWarehouseId, classNameId, classPK);