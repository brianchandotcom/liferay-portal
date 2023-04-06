create index IX_6F2F7695 on CPMethodGroupRelQualifier (CPaymentMethodGroupRelId);
create index IX_60685C93 on CPMethodGroupRelQualifier (classNameId, CPaymentMethodGroupRelId);
create unique index IX_C17FAAA on CPMethodGroupRelQualifier (classNameId, classPK, CPaymentMethodGroupRelId);

create index IX_62597F70 on CommercePaymentEntry (companyId, classNameId, classPK);

create index IX_8BE29B30 on CommercePaymentEntryAudit (commercePaymentEntryId);
create index IX_DA95CEA4 on CommercePaymentEntryAudit (companyId, commercePaymentEntryId);

create index IX_98EF79EB on CommercePaymentMethodGroupRel (groupId, active_);
create unique index IX_BE0A338F on CommercePaymentMethodGroupRel (groupId, engineKey[$COLUMN_LENGTH:75$]);