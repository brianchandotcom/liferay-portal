create index IX_92D439B7 on CPricingClassCPDefinitionRel (CPDefinitionId, ctCollectionId);
create unique index IX_DA09B6F3 on CPricingClassCPDefinitionRel (commercePricingClassId, CPDefinitionId, ctCollectionId);
create index IX_CD543364 on CPricingClassCPDefinitionRel (commercePricingClassId, ctCollectionId);

create index IX_2D86244A on CommercePriceModifier (commercePriceListId, ctCollectionId);
create index IX_A545A51B on CommercePriceModifier (companyId, ctCollectionId);
create index IX_8C8A963E on CommercePriceModifier (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_B4BA62E0 on CommercePriceModifier (companyId, target[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_73519559 on CommercePriceModifier (displayDate, status, ctCollectionId);
create index IX_C466FB00 on CommercePriceModifier (expirationDate, status, ctCollectionId);
create index IX_5267D28D on CommercePriceModifier (groupId, companyId, status, ctCollectionId);
create index IX_72AB41D on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_63A1C527 on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);
create unique index IX_FAB45A5F on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId);

create index IX_6B47904D on CommercePriceModifierRel (classNameId, classPK, ctCollectionId);
create unique index IX_510AD1A9 on CommercePriceModifierRel (commercePriceModifierId, classNameId, classPK, ctCollectionId);
create index IX_2BD553F6 on CommercePriceModifierRel (commercePriceModifierId, classNameId, ctCollectionId);
create index IX_153045D4 on CommercePriceModifierRel (commercePriceModifierId, ctCollectionId);

create index IX_1D109633 on CommercePricingClass (companyId, ctCollectionId);
create index IX_925EA26 on CommercePricingClass (companyId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId);
create index IX_D07AD805 on CommercePricingClass (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId);
create index IX_C601023F on CommercePricingClass (uuid_[$COLUMN_LENGTH:75$], ctCollectionId);