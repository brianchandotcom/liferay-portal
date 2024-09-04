create unique index IX_D9799B2A on CPMethodGroupRelQualifier (CPaymentMethodGroupRelId, classNameId, classPK);

create index IX_DF716143 on CommercePaymentEntry (companyId, classNameId, classPK, type_, paymentStatus);
create unique index IX_ECEC8382 on CommercePaymentEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);

create index IX_8BE29B30 on CommercePaymentEntryAudit (commercePaymentEntryId);

create index IX_98EF79EB on CommercePaymentMethodGroupRel (groupId, active_);
create unique index IX_FFF17D63 on CommercePaymentMethodGroupRel (groupId, paymentIntegrationKey[$COLUMN_LENGTH:75$]);