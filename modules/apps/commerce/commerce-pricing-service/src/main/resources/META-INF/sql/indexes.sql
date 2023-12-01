create unique index IX_7CBDB1F3 on CPricingClassCPDefinitionRel (CPDefinitionId, commercePricingClassId, ctCollectionId);

create index IX_176CA5EC on CommercePriceModifier (commercePriceListId);
create index IX_FCACD082 on CommercePriceModifier (companyId, target[$COLUMN_LENGTH:75$]);
create index IX_70709A52 on CommercePriceModifier (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_610EA573 on CommercePriceModifier (status, companyId, groupId);
create index IX_6A13CEF on CommercePriceModifier (status, displayDate);
create index IX_921ADDA2 on CommercePriceModifier (status, expirationDate);
create index IX_5C17A0C9 on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_65CB769F on CommercePriceModifierRel (classNameId, classPK, commercePriceModifierId, ctCollectionId);

create index IX_B58209D5 on CommercePricingClass (companyId);
create index IX_D2CFD76A on CommercePricingClass (externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_33040DE1 on CommercePricingClass (uuid_[$COLUMN_LENGTH:75$]);