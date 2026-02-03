create unique index IX_1D29E189 on CSFixedOptionQualifier (commerceShippingFixedOptionId, classNameId, classPK);

create index IX_770D8D7D on CShippingFixedOptionRel (commerceShippingFixedOptionId, commerceShippingMethodId);
create index IX_4AA09D60 on CShippingFixedOptionRel (commerceShippingMethodId);

create index IX_DCB21C1F on CommerceShippingFixedOption (commerceShippingMethodId);
create unique index IX_BCEAE976 on CommerceShippingFixedOption (companyId, key_[$COLUMN_LENGTH:75$]);