import reducer from '../settings';
import {Map} from 'immutable';
import {actionTypes as projectsActionTypes} from 'shared/actions/projects';
import {actionTypes as settingsActionTypes} from 'shared/actions/settings';

describe('settings reducer', () => {
	it('should set back url', () => {
		const url = 'foo/bar';

		const action = {
			payload: {
				url
			},
			type: settingsActionTypes.SET_BACK_URL
		};

		const state = reducer(new Map(), action);

		expect(state.get('backURL')).toBe(url);
	});

	it('should update back url on UPDATE_PROJECT_SUCCESS', () => {
		const prevId = 'bar';
		const url = 'foo/bar/stuff';

		const action = {
			meta: {
				newId: 'test',
				prevId
			},
			payload: {entities: {projects: {[prevId]: {data: {}}}}},
			type: projectsActionTypes.UPDATE_PROJECT_SUCCESS
		};

		const state = reducer(new Map({backURL: url}), action);

		expect(state.get('backURL')).toBe('foo/test/stuff');
	});
});
