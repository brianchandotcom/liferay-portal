create index IX_7951AAEB on CNTemplateCAccountGroupRel (commerceAccountGroupId);
create unique index IX_AFBF7DA on CNTemplateCAccountGroupRel (commerceNotificationTemplateId, commerceAccountGroupId);

create index IX_6E9D8183 on CNotificationAttachment (CNotificationQueueEntryId);
create unique index IX_339EA78D on CNotificationAttachment (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F9149FC on CommerceNotificationQueueEntry (commerceNotificationTemplateId);
create index IX_56F7649E on CommerceNotificationQueueEntry (groupId, sent, classNameId, classPK);
create index IX_BEFF6FD9 on CommerceNotificationQueueEntry (sent);
create index IX_80026CA7 on CommerceNotificationQueueEntry (sentDate);

create index IX_6D6C3008 on CommerceNotificationTemplate (groupId, enabled, type_[$COLUMN_LENGTH:75$]);
create unique index IX_56F147B0 on CommerceNotificationTemplate (groupId, uuid_[$COLUMN_LENGTH:75$]);
create index IX_753B890E on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$]);