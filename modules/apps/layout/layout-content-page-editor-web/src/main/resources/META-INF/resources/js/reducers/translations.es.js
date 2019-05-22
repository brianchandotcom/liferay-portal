import {CHANGE_LANGUAGE_ID} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * Reducer for changing languageId
 * @param {object} state
 * @param {object} action
 * @param {string} action.languageId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function languageIdReducer(state, action) {
	let nextState = state;

	if (action.type === CHANGE_LANGUAGE_ID) {
		nextState = setIn(nextState, ['languageId'], action.languageId);
	}

	return nextState;
}

export {languageIdReducer};
