create index IX_238DA0C8 on CommerceDiscount (groupId);
create index IX_687F1796 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_CF48B98 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_CB9E6769 on CommerceDiscountRule (commerceDiscountId);

create index IX_A9530E3E on CommerceDiscountUserSegmentRel (commerceDiscountId);
create index IX_1DFDF4E7 on CommerceDiscountUserSegmentRel (commerceUserSegmentEntryId);