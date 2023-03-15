import sendRequest from 'shared/util/request';

export const fetchNotifications = ({groupId, type}) =>
	sendRequest({
		data: {type},
		method: 'GET',
		path: `main/${groupId}/notification`
	});

export const deleteNotification = (groupId: string, notificationId: string) =>
	sendRequest({
		method: 'DELETE',
		path: `main/${groupId}/notification/${notificationId}`
	});

export const readNotification = (groupId: string, notificationId: string) =>
	sendRequest({
		method: 'POST',
		path: `main/${groupId}/notification/${notificationId}/read`
	});
