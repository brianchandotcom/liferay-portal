import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../actions/actions.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {boolean} payload.savingChanges
 * @param {Date} payload.lastSaveDate
 * @return {object}
 * @review
 */
function saveChangesReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === UPDATE_LAST_SAVE_DATE) {
		nextState = Object.assign({}, nextState);
		nextState.lastSaveDate = payload.lastSaveDate.toLocaleTimeString(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
	}
	else if (actionType === UPDATE_SAVING_CHANGES_STATUS) {
		nextState = Object.assign({}, nextState);
		nextState.savingChanges = Boolean(payload.savingChanges);
	}

	return nextState;
}

export {saveChangesReducer};