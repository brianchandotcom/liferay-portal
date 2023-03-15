import Promise from 'metal-promise';
import {mockFieldMapping, mockSearch} from 'test/data';

export const fetch = jest.fn(() => Promise.resolve(mockFieldMapping()));

export const fetchDefault = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);

export const fetchSuggestions = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);

export const create = jest.fn(() => Promise.resolve(mockFieldMapping()));

export const search = jest.fn(() =>
	Promise.resolve(mockSearch(mockFieldMapping))
);
