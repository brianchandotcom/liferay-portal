create index IX_42E60133 on NQueueEntryAttachment (notificationQueueEntryId);

create unique index IX_8F1205E1 on NTemplateAttachment (notificationTemplateId, objectFieldId);

create index IX_83DBCE06 on NotificationQueueEntry (notificationTemplateId);
create index IX_3B9F9C6C on NotificationQueueEntry (sentDate);
create index IX_4A9516F8 on NotificationQueueEntry (status);
create index IX_74855369 on NotificationQueueEntry (type_[$COLUMN_LENGTH:75$], status);

create index IX_AE7F6E5F on NotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);