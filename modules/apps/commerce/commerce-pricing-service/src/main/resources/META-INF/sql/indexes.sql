create unique index IX_7CBDB1F3 on CPricingClassCPDefinitionRel (CPDefinitionId, commercePricingClassId, ctCollectionId);

create index IX_176CA5EC on CommercePriceModifier (commercePriceListId);
create unique index IX_DB76B9C2 on CommercePriceModifier (companyId, ctCollectionId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_AEE7A167 on CommercePriceModifier (companyId, status, groupId);
create index IX_FCACD082 on CommercePriceModifier (companyId, target[$COLUMN_LENGTH:75$]);
create unique index IX_2AB03437 on CommercePriceModifier (groupId, ctCollectionId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_6A13CEF on CommercePriceModifier (status, displayDate);
create index IX_921ADDA2 on CommercePriceModifier (status, expirationDate);

create unique index IX_65CB769F on CommercePriceModifierRel (classNameId, classPK, commercePriceModifierId, ctCollectionId);

create unique index IX_925EA26 on CommercePricingClass (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_33040DE1 on CommercePricingClass (uuid_[$COLUMN_LENGTH:75$]);