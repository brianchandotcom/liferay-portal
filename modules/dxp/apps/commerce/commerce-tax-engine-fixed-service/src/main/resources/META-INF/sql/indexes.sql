create unique index IX_7F86B26F on CommerceTaxFixedRate (commerceTaxCategoryId);
create index IX_52767DD2 on CommerceTaxFixedRate (commerceTaxMethodId);

create index IX_8798C6EA on CommerceTaxFixedRateAddressRel (commerceTaxCategoryId);
create index IX_CB69750D on CommerceTaxFixedRateAddressRel (commerceTaxMethodId);