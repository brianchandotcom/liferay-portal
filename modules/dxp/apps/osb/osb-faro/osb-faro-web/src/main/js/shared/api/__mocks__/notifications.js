import Promise from 'metal-promise';

export const fetchNotifications = jest.fn(() => Promise.resolve([]));

export const readNotification = jest.fn(() => Promise.resolve(''));
