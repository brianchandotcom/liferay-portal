create unique index IX_D25D94B1 on CommerceTaxCategoryMapping (commerceTaxMethodId, CPTaxCategoryId);
create unique index IX_19A18A31 on CommerceTaxCategoryMapping (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);
create unique index IX_FAAA4E5C on CommerceTaxCategoryMapping (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F3810116 on CommerceTaxMethod (groupId, active_);
create unique index IX_BA569BFA on CommerceTaxMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);