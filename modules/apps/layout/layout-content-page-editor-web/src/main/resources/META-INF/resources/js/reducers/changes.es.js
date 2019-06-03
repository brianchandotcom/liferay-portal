import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../actions/actions.es';

/**
 * @param {!object} state
 * @param {object} action
 * @param {Date} action.lastSaveDate
 * @param {boolean} action.savingChanges
 * @param {string} action.type
 * @return {object}
 * @review
 */
function saveChangesReducer(state, action) {
	let nextState = state;

	if (action.type === UPDATE_LAST_SAVE_DATE) {
		const newDate = action.lastSaveDate.toLocaleTimeString(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);

		nextState = setIn(nextState, ['lastSaveDate'], newDate);
	} else if (action.type === UPDATE_SAVING_CHANGES_STATUS) {
		nextState = setIn(
			nextState,
			['savingChanges'],
			Boolean(action.savingChanges)
		);
	}

	return nextState;
}

export {saveChangesReducer};
