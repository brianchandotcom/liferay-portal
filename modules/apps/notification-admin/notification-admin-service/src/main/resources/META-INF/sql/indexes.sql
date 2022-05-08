create index IX_B8319EF9 on NotificationQueueEntry (groupId, classNameId, classPK, sent);
create index IX_83DBCE06 on NotificationQueueEntry (notificationTemplateId);
create index IX_654D4A1E on NotificationQueueEntry (sent);
create index IX_3B9F9C6C on NotificationQueueEntry (sentDate);

create index IX_66700532 on NotificationTemplate (groupId, enabled);
create index IX_AE7F6E5F on NotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_CF7E00A1 on NotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);