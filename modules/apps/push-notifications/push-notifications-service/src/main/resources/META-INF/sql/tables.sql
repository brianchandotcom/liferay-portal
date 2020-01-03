create table PushNotificationsDevice (
	pushNotificationsDeviceId LONG not null,
	companyId LONG not null,
	userId LONG,
	createDate DATE null,
	platform VARCHAR(75) null,
	token STRING null,
	primary key (pushNotificationsDeviceId, companyId)
);