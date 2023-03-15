import * as data from 'test/data';
import Promise from 'metal-promise';

export const fetchGroup = jest.fn(() =>
	Promise.resolve({
		items: [data.mockActivity(2)],
		total: 1
	})
);

export const fetchHistory = jest.fn(() =>
	Promise.resolve(data.mockActivityHistory())
);

export const searchCount = jest.fn(() => Promise.resolve(0));
