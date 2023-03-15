import reducer from '../projects';
import {actionTypes} from '../../actions/projects';
import {fromJS, OrderedMap} from 'immutable';
import {mockProject} from 'test/data';
import {RemoteData} from 'shared/util/records';

describe('Projects Reducer', () => {
	it('should be a function', () => {
		expect(typeof reducer).toBe('function');
	});

	it('should update the project key on UPDATE_PROJECT_SUCCESS', () => {
		const newId = 'bar';
		const prevId = 'foo';

		const action = {
			meta: {newId, prevId},
			payload: {
				entities: {projects: {[prevId]: {data: {}}}},
				id: prevId
			},
			type: actionTypes.UPDATE_PROJECT_SUCCESS
		};

		const prevState = new OrderedMap({
			[prevId]: new RemoteData({
				data: fromJS(mockProject(1))
			})
		});

		expect(prevState.get(newId)).toBeFalsy();
		expect(prevState.get(prevId)).toBeTruthy();

		const newState = reducer(prevState, action);

		expect(newState.get(newId)).toBeTruthy();
		expect(newState.get(prevId)).toBeFalsy();
	});
});
