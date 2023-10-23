import {mockAsset, mockSearch} from 'test/data';

export const search = jest.fn(() => Promise.resolve(mockSearch(mockAsset)));
