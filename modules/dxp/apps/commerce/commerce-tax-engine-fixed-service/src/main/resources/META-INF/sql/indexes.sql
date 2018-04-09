create unique index IX_7F86B26F on CommerceTaxFixedRate (commerceTaxCategoryId);
create unique index IX_9E7BEC07 on CommerceTaxFixedRate (commerceTaxMethodId, commerceTaxCategoryId);

create index IX_8798C6EA on CommerceTaxFixedRateAddressRel (commerceTaxCategoryId);
create index IX_CB69750D on CommerceTaxFixedRateAddressRel (commerceTaxMethodId);