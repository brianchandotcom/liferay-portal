create index IX_6F2F7695 on CPMethodGroupRelQualifier (CPaymentMethodGroupRelId);
create index IX_60685C93 on CPMethodGroupRelQualifier (classNameId, CPaymentMethodGroupRelId);
create unique index IX_C17FAAA on CPMethodGroupRelQualifier (classNameId, classPK, CPaymentMethodGroupRelId);

create index IX_62597F70 on CommercePaymentEntry (companyId, classNameId, classPK);

create index IX_8BE29B30 on CommercePaymentEntryAudit (commercePaymentEntryId);

create index IX_98EF79EB on CommercePaymentMethodGroupRel (groupId, active_);
create unique index IX_FFF17D63 on CommercePaymentMethodGroupRel (groupId, paymentIntegrationKey[$COLUMN_LENGTH:75$]);