create index IX_C83537E on CVirtualOrderItemFileEntry (commerceVirtualOrderItemId, fileEntryId);
create unique index IX_68E33939 on CVirtualOrderItemFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_44EADF9A on CommerceVirtualOrderItem (commerceOrderItemId);
create unique index IX_81F354CD on CommerceVirtualOrderItem (uuid_[$COLUMN_LENGTH:75$], groupId);