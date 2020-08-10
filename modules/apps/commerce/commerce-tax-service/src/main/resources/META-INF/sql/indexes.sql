create index IX_F3810116 on CommerceTaxMethod (groupId, active_);
create unique index IX_BA569BFA on CommerceTaxMethod (groupId, engineKey[$COLUMN_LENGTH:75$]);