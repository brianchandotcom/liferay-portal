create index IX_83DBCE06 on NotificationQueueEntry (notificationTemplateId);

create index IX_AE7F6E5F on NotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);