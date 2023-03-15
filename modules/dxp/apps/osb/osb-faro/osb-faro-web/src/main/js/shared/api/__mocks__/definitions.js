import * as data from 'test/data';
import Promise from 'metal-promise';

export const searchIndividualAttributes = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividualAttributes, 5))
);
