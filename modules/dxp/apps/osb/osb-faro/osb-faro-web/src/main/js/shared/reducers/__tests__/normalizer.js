import reducer from '../normalizer';
import {Map} from 'immutable';
import {
	mockAccount,
	mockCSVDataSource,
	mockIndividual,
	mockProject,
	mockSegment
} from 'test/data';

describe('Normalizer Reducer', () => {
	it('should update store with normalized entities', () => {
		const action = {
			payload: {
				entities: {
					accounts: {1: mockAccount(1)},
					dataSources: {2: mockCSVDataSource(2)},
					individuals: {3: mockIndividual(3)},
					projects: {15: mockProject(15)},
					segments: {4: mockSegment(4)}
				}
			}
		};

		const state = reducer(new Map(), action);

		expect(state).toMatchSnapshot();
	});

	it('should return the original state if no entities key is found', () => {
		const initialState = new Map();

		const state = reducer(initialState, {});

		expect(state).toBe(initialState);
	});

	it('should return a Map by default', () => {
		const state = reducer(undefined, {});

		expect(state).toBeInstanceOf(Map);
	});
});
