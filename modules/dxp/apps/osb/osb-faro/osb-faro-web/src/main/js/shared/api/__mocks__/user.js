import Promise from 'metal-promise';
import {mockSearch, mockUser} from 'test/data';

const delete$ = jest.fn(() => Promise.resolve(''));

export {delete$ as delete};

export const fetchCurrentUser = jest.fn(() => Promise.resolve(mockUser('23')));

export const fetchCount = jest.fn(() => Promise.resolve(mockSearch(mockUser)));

export const inviteMany = jest.fn(() => Promise.resolve([mockUser()]));

export const fetchMany = jest.fn(() => Promise.resolve(mockSearch(mockUser)));

export const updateMany = jest.fn(() => Promise.resolve([mockUser()]));
