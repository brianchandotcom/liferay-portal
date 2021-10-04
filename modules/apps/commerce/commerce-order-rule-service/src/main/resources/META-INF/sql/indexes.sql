create index IX_D585F2B on CommerceOrderRuleEntry (companyId, active_, type_[$COLUMN_LENGTH:75$]);
create index IX_28877FDE on CommerceOrderRuleEntry (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_CE335E76 on CommerceOrderRuleEntry (companyId, type_[$COLUMN_LENGTH:75$]);
create index IX_6E9FF03D on CommerceOrderRuleEntry (displayDate, status);
create index IX_FDBBEE20 on CommerceOrderRuleEntry (expirationDate, status);
create index IX_30ADB7AC on CommerceOrderRuleEntry (type_[$COLUMN_LENGTH:75$]);

create unique index IX_C286F5C3 on CommerceOrderRuleEntryRel (classNameId, classPK, commerceOrderRuleEntryId);
create index IX_84C4F644 on CommerceOrderRuleEntryRel (classNameId, commerceOrderRuleEntryId);
create index IX_6FFD80AE on CommerceOrderRuleEntryRel (commerceOrderRuleEntryId);