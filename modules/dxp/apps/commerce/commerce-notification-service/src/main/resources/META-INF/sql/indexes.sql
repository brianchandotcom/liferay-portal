create index IX_48D9EF1E on CENTemplate (groupId);
create index IX_E9D68A00 on CENTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_1B799082 on CENTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);

create unique index IX_7DEAC57F on CNTemplateUserSegmentRel (commerceNotificationTemplateId, commerceUserSegmentEntryId);
create index IX_355FC10 on CNTemplateUserSegmentRel (commerceUserSegmentEntryId);

create index IX_1C826584 on CommerceNotificationTemplate (groupId);
create index IX_E295E65A on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_89383B5C on CommerceNotificationTemplate (uuid_[$COLUMN_LENGTH:75$], groupId);