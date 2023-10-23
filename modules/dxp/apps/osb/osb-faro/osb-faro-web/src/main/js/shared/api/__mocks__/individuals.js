import * as data from 'test/data';

export const fetch = jest.fn(() => Promise.resolve(data.mockIndividual()));

export const fetchDetails = jest.fn(() =>
	Promise.resolve(data.mockIndividualDetails())
);

export const fetchEnrichedProfilesCount = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);

export const fetchMembership = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);

export const fetchFieldValues = jest.fn(() =>
	Promise.resolve(data.mockSearch(String))
);

export const search = jest.fn(() =>
	Promise.resolve(data.mockSearch(data.mockIndividual))
);
