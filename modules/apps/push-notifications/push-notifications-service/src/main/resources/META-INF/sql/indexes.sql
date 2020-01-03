create unique index IX_CCEF5E29 on PushNotificationsDevice (token[$COLUMN_LENGTH:4000$], companyId);
create index IX_2FBF066B on PushNotificationsDevice (userId, platform[$COLUMN_LENGTH:75$]);