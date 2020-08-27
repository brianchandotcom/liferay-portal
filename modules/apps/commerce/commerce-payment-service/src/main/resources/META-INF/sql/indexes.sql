create index IX_98EF79EB on CommercePaymentMethodGroupRel (groupId, active_);
create unique index IX_BE0A338F on CommercePaymentMethodGroupRel (groupId, engineKey[$COLUMN_LENGTH:75$]);