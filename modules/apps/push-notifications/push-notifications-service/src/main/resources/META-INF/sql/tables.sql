create table PushNotificationsDevice (
	pushNotificationsDeviceId LONG not null primary key,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	platform VARCHAR(75) null,
	token STRING null
);