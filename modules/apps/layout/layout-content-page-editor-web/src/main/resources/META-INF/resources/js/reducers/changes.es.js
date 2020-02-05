/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	UPDATE_LAST_SAVE_DATE,
	UPDATE_SAVING_CHANGES_STATUS
} from '../actions/actions.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';

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
	}
	else if (action.type === UPDATE_SAVING_CHANGES_STATUS) {
		nextState = setIn(
			nextState,
			['savingChanges'],
			Boolean(action.savingChanges)
		);
	}

	return nextState;
}

export {saveChangesReducer};
