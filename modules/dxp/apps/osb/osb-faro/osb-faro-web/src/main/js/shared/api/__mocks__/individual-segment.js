import Promise from 'metal-promise';
import {
	mockMembershipChange,
	mockMembershipChangeAggregation,
	mockSearch,
	mockSegment
} from 'test/data';

export const addIndividuals = jest.fn(() => Promise.resolve(mockSegment()));

const delete$ = jest.fn(() => Promise.resolve(''));

export {delete$ as delete};

export const fetch = jest.fn(() => Promise.resolve(mockSegment()));

export const create = jest.fn(() => Promise.resolve(mockSegment()));

export const update = jest.fn(() => Promise.resolve(mockSegment()));

export const updateChannel = jest.fn(() => Promise.resolve(mockSegment()));

export const addMemberships = jest.fn(() => Promise.resolve(mockSegment()));

export const removeMemberships = jest.fn(() => Promise.resolve(mockSegment()));

export const fetchMembershipChanges = jest.fn(() =>
	Promise.resolve(mockSearch(mockMembershipChange))
);

export const fetchMembershipChangesAggregations = jest.fn(() =>
	Promise.resolve([mockMembershipChangeAggregation()])
);

export const search = jest.fn(() => Promise.resolve(mockSearch(mockSegment)));

export const searchUnassigned = jest.fn(() =>
	Promise.resolve(mockSearch(mockSegment))
);
