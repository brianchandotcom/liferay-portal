create unique index IX_221AEC7F on CSFixedOptionQualifier (classNameId, classPK, commerceShippingFixedOptionId);
create index IX_F58BD6F4 on CSFixedOptionQualifier (classNameId, commerceShippingFixedOptionId);
create index IX_9ABCCE34 on CSFixedOptionQualifier (commerceShippingFixedOptionId);

create index IX_D89A7E24 on CShippingFixedOptionRel (commerceShippingFixedOptionId);
create index IX_4AA09D60 on CShippingFixedOptionRel (commerceShippingMethodId);

create index IX_DCB21C1F on CommerceShippingFixedOption (commerceShippingMethodId);
create unique index IX_8F8E889A on CommerceShippingFixedOption (key_[$COLUMN_LENGTH:75$]);