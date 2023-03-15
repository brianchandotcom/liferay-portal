import Promise from 'metal-promise';
import {mockInterestData, mockSearch} from 'test/data';

export const fetch = jest.fn(() => Promise.resolve(mockInterestData()));

export const search = jest.fn(() =>
	Promise.resolve(mockSearch(mockInterestData))
);

export const searchKeywords = jest.fn(() =>
	Promise.resolve(mockSearch((i, name) => `${name}-${i}`, 5, ['test']))
);
