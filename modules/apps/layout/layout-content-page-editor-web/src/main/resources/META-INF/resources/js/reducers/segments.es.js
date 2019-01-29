import {CHANGE_SEGMENT_ID} from '../actions/actions.es';
import {setIn} from '../utils/utils.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {string} payload.segmentId
 * @return {object}
 * @review
 */
function segmentIdReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CHANGE_SEGMENT_ID) {
		nextState = setIn(nextState, ['segmentId'], payload.segmentId);
	}

	return nextState;
}

export {segmentIdReducer};