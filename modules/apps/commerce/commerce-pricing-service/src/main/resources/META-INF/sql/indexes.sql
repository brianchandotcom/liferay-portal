create index IX_31653559 on CPricingClassCPDefinitionRel (CPDefinitionId);
create unique index IX_AC54AA95 on CPricingClassCPDefinitionRel (commercePricingClassId, CPDefinitionId);

create index IX_176CA5EC on CommercePriceModifier (commercePriceListId);
create index IX_335B7FE0 on CommercePriceModifier (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_FCACD082 on CommercePriceModifier (companyId, target[$COLUMN_LENGTH:75$]);
create index IX_C60214FB on CommercePriceModifier (displayDate, status);
create index IX_E3CDA8A2 on CommercePriceModifier (expirationDate, status);
create index IX_93813A2F on CommercePriceModifier (groupId, companyId, status);
create index IX_EED93BBF on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_A4ABA601 on CommercePriceModifier (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_391477EF on CommercePriceModifierRel (classNameId, classPK);
create unique index IX_89DDF14B on CommercePriceModifierRel (commercePriceModifierId, classNameId, classPK);

create index IX_FCABA3C8 on CommercePricingClass (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_287E2FA7 on CommercePricingClass (uuid_[$COLUMN_LENGTH:75$], companyId);