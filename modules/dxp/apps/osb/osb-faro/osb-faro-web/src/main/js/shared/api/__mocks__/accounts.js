import * as data from 'test/data';
import Promise from 'metal-promise';

export const fetch = jest.fn(() => Promise.resolve(data.mockAccount()));

export const fetchDetails = jest.fn(() =>
	Promise.resolve(data.mockAccountDetails())
);

export const search = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockAccount))
);
