create unique index IX_2F3EDC9F on PushNotificationsDevice (token[$COLUMN_LENGTH:4000$]);
create index IX_2FBF066B on PushNotificationsDevice (userId, platform[$COLUMN_LENGTH:75$]);