create index IX_29083CDC on NotificationsQueueEntry (groupId, classNameId, classPK, sent);
create index IX_60F00EBE on NotificationsQueueEntry (notificationsTemplateId);
create index IX_BABB9C9B on NotificationsQueueEntry (sent);
create index IX_B4766869 on NotificationsQueueEntry (sentDate);

create index IX_3F4B1F6F on NotificationsTemplate (groupId, enabled);
create index IX_875A889C on NotificationsTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_27E4861E on NotificationsTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);