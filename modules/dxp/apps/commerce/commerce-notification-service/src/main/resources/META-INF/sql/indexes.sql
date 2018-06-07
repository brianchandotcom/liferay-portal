create unique index IX_7DEAC57F on CNTemplateUserSegmentRel (commerceNotificationTemplateId, commerceUserSegmentEntryId);
create index IX_355FC10 on CNTemplateUserSegmentRel (commerceUserSegmentEntryId);

create index IX_6E9D8183 on CNotificationAttachment (CNotificationQueueEntryId);
create index IX_8D120A4B on CNotificationAttachment (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_339EA78D on CNotificationAttachment (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_F9149FC on CommerceNotificationQueueEntry (commerceNotificationTemplateId);
create index IX_2D3E5B3D on CommerceNotificationQueueEntry (groupId);
create index IX_BEFF6FD9 on CommerceNotificationQueueEntry (sent);
create index IX_80026CA7 on CommerceNotificationQueueEntry (sentDate);

create index IX_9A867D2D on CommerceNotificationTemplate (groupId, enabled);
create index IX_AFC4A180 on CommerceNotificationTemplate (groupId, type_[$COLUMN_LENGTH:75$], enabled);
create index IX_E295E65A on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_89383B5C on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);