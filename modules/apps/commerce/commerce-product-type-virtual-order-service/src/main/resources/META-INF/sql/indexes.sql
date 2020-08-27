create unique index IX_44EADF9A on CommerceVirtualOrderItem (commerceOrderItemId);
create index IX_98F0678B on CommerceVirtualOrderItem (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_81F354CD on CommerceVirtualOrderItem (uuid_[$COLUMN_LENGTH:75$], groupId);