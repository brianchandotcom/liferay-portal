create index IX_7951AAEB on CNTemplateCAccountGroupRel (commerceAccountGroupId);
create unique index IX_AFBF7DA on CNTemplateCAccountGroupRel (commerceNotificationTemplateId, commerceAccountGroupId);

create index IX_6E9D8183 on CNotificationAttachment (CNotificationQueueEntryId);
create index IX_8D120A4B on CNotificationAttachment (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_339EA78D on CNotificationAttachment (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_8D07A6AE on CommerceNotificationQueueEntry (classNameId, classPK, sent);
create index IX_F9149FC on CommerceNotificationQueueEntry (commerceNotificationTemplateId);
create index IX_1CB981DE on CommerceNotificationQueueEntry (groupId, classNameId, classPK, sent);
create index IX_BEFF6FD9 on CommerceNotificationQueueEntry (sent);
create index IX_80026CA7 on CommerceNotificationQueueEntry (sentDate);

create index IX_9A867D2D on CommerceNotificationTemplate (groupId, enabled);
create index IX_AFC4A180 on CommerceNotificationTemplate (groupId, type_[$COLUMN_LENGTH:75$], enabled);
create index IX_E295E65A on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_89383B5C on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);