create unique index IX_ECA6B935 on CommerceTaxFixedRate (commerceTaxMethodId, CPTaxCategoryId);

create index IX_37AE3A58 on CommerceTaxFixedRateAddressRel (CPTaxCategoryId);
create index IX_CB69750D on CommerceTaxFixedRateAddressRel (commerceTaxMethodId);
create index IX_DB83CD12 on CommerceTaxFixedRateAddressRel (countryId);