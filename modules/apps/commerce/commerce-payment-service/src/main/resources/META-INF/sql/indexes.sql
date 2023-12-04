create index IX_6F2F7695 on CPMethodGroupRelQualifier (CPaymentMethodGroupRelId);
create index IX_60685C93 on CPMethodGroupRelQualifier (classNameId, CPaymentMethodGroupRelId);
create unique index IX_C17FAAA on CPMethodGroupRelQualifier (classNameId, classPK, CPaymentMethodGroupRelId);

create index IX_B481AC65 on CommercePaymentEntry (companyId, classNameId, classPK, type_);
create unique index IX_D4FD2638 on CommercePaymentEntry (externalReferenceCode[$COLUMN_LENGTH:75$], companyId);

create index IX_8BE29B30 on CommercePaymentEntryAudit (commercePaymentEntryId);

create index IX_98EF79EB on CommercePaymentMethodGroupRel (groupId, active_);
create unique index IX_FFF17D63 on CommercePaymentMethodGroupRel (groupId, paymentIntegrationKey[$COLUMN_LENGTH:75$]);