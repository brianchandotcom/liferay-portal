create index IX_42E60133 on NQueueEntryAttachment (notificationQueueEntryId);

create unique index IX_8F1205E1 on NTemplateAttachment (notificationTemplateId, objectFieldId);

create index IX_559C5AD6 on NotificationQueueEntry (companyId);
create index IX_83DBCE06 on NotificationQueueEntry (notificationTemplateId);
create index IX_3B9F9C6C on NotificationQueueEntry (sentDate);
create index IX_74855369 on NotificationQueueEntry (type_[$COLUMN_LENGTH:75$], status);

create index IX_470340CF on NotificationRecipient (classPK);
create index IX_2ADCE1A0 on NotificationRecipient (uuid_[$COLUMN_LENGTH:75$]);

create index IX_B6D4DBB0 on NotificationRecipientSetting (notificationRecipientId, name[$COLUMN_LENGTH:75$]);
create index IX_5B9A04C on NotificationRecipientSetting (uuid_[$COLUMN_LENGTH:75$]);

create unique index IX_7E887280 on NotificationTemplate (companyId, externalReferenceCode[$COLUMN_LENGTH:75$]);
create index IX_7256D229 on NotificationTemplate (uuid_[$COLUMN_LENGTH:75$]);