import * as data from 'test/data';

export const searchIndividualAttributes = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividualAttributes, 5))
);
