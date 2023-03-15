import * as API from 'shared/api';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {segment} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'segment')
};

export function fetchSegment(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.individualSegment.fetch,
				schema: segment,
				types: [
					actionTypes.FETCH_SEGMENT_REQUEST,
					actionTypes.FETCH_SEGMENT_SUCCESS,
					actionTypes.FETCH_SEGMENT_FAILURE
				]
			}
		},
		payload: {
			id: data.segmentId
		},
		type: 'NO_OP'
	};
}
