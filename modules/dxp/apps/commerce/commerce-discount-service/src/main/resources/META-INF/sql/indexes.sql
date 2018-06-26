create index IX_122C15C4 on CommerceDiscount (displayDate, status);
create index IX_2FBF0739 on CommerceDiscount (expirationDate, status);
create index IX_3ACFDDAF on CommerceDiscount (groupId, couponCode[$COLUMN_LENGTH:75$]);
create index IX_687F1796 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_CF48B98 on CommerceDiscount (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_6B4EEC38 on CommerceDiscountRel (classNameId, classPK);
create index IX_DDFDEF40 on CommerceDiscountRel (commerceDiscountId, classNameId);

create index IX_CB9E6769 on CommerceDiscountRule (commerceDiscountId);

create index IX_A9AFCFB7 on CommerceDiscountUsageEntry (groupId);

create index IX_A9530E3E on CommerceDiscountUserSegmentRel (commerceDiscountId);
create index IX_1DFDF4E7 on CommerceDiscountUserSegmentRel (commerceUserSegmentEntryId);