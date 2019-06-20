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
