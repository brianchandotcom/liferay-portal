import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../actionTypes.es';

/**
 * @param {!MetalStore} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {boolean} payload.savingChanges
 * @param {Date} payload.lastSaveDate
 * @return {MetalStore}
 * @review
 */

function saveChangesReducer(state, actionType, payload) {
	const nextState = Object.assign({}, state);

	if (actionType === UPDATE_LAST_SAVE_DATE) {
		nextState.lastSaveDate = payload.lastSaveDate.toLocaleTimeString(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
	}
	else if (actionType === UPDATE_SAVING_CHANGES_STATUS) {
		nextState.savingChanges = !!payload.savingChanges;
	}

	return nextState;
}

export {saveChangesReducer};
export default saveChangesReducer;