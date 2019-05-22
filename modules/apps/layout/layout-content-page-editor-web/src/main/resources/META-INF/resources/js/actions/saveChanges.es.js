import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from './actions.es';

/**
 * @return {object}
 */
function disableSavingChangesStatusAction() {
	return {
		savingChanges: false,
		type: UPDATE_SAVING_CHANGES_STATUS
	};
}

/**
 * @return {object}
 */
function enableSavingChangesStatusAction() {
	return {
		savingChanges: true,
		type: UPDATE_SAVING_CHANGES_STATUS
	};
}

/**
 * @param {Date} [date=new Date()]
 * @return {object}
 */
function updateLastSaveDateAction(date = new Date()) {
	return {
		lastSaveDate: date,
		type: UPDATE_LAST_SAVE_DATE
	};
}

export {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
};
