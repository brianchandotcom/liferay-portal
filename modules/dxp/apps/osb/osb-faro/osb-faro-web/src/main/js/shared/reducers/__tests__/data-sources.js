import reducer from '../data-sources';
import {actionTypes} from '../../actions/data-sources';
import {Map} from 'immutable';
import {RemoteData} from 'shared/util/records';

describe('data-sources', () => {
	function getResult(action, id, initialiState) {
		return reducer(initialiState, action).get(String(id));
	}

	it('should handle fetch data source request', () => {
		const id = 'foo';

		const action = {
			payload: {
				id
			},
			type: actionTypes.FETCH_DATA_SOURCE_REQUEST
		};

		expect(getResult(action, id)).toMatchObject(new RemoteData());
	});

	it('should handle fetch data source failure', () => {
		const id = 'foo';

		const action = {
			payload: {
				id
			},
			type: actionTypes.FETCH_DATA_SOURCE_FAILURE
		};

		expect(getResult(action, id)).toMatchObject(
			new RemoteData({data: null, error: true, loading: false})
		);
	});

	it('should handle delete data source success', () => {
		const id = 'foo';

		const action = {
			meta: {id},
			payload: {
				id
			},
			type: actionTypes.DELETE_DATA_SOURCE_SUCCESS
		};

		expect(getResult(action, id, new Map({[id]: {id}}))).toBeFalsy();
	});
});
