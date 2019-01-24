import {CHANGE_SEGMENT_ID} from '../actions/actions.es';
import {setIn} from '../../utils/utils.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} actionType
 * @return {object}
 * @review
 */
function switchToSegmentReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CHANGE_SEGMENT_ID) {
		nextState = setIn(nextState, ['segmentId'], payload.segmentId);
	}

	return nextState;
}

export {
	switchToSegmentReducer
};