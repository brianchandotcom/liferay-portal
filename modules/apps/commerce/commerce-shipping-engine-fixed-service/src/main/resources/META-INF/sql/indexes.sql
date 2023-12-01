create unique index IX_1D29E189 on CSFixedOptionQualifier (commerceShippingFixedOptionId, classNameId, classPK);

create index IX_D89A7E24 on CShippingFixedOptionRel (commerceShippingFixedOptionId);
create index IX_4AA09D60 on CShippingFixedOptionRel (commerceShippingMethodId);

create index IX_DCB21C1F on CommerceShippingFixedOption (commerceShippingMethodId);
create unique index IX_BCEAE976 on CommerceShippingFixedOption (companyId, key_[$COLUMN_LENGTH:75$]);