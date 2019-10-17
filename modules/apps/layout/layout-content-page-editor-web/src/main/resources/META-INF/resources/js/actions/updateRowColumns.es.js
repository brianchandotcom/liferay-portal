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

import {updatePageEditorLayoutData} from '../utils/FragmentsEditorFetchUtils.es';
import {getRowIndex} from '../utils/FragmentsEditorGetUtils.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	UPDATE_ROW_COLUMNS_ERROR,
	UPDATE_ROW_COLUMNS_LOADING
} from './actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from './saveChanges.es';

/**
 * @param {Array} columns
 * @param {string} rowId
 * @return {function}
 * @review
 */
function updateRowColumnsAction(columns, rowId) {
	return function(dispatch, getState) {
		const state = getState();

		const rowIndex = getRowIndex(state.layoutData.structure, rowId);

		const previousData = state.layoutData;

		let nextData = previousData;

		if (rowIndex !== -1) {
			nextData = setIn(
				previousData,
				['structure', rowIndex.toString(), 'columns'],
				columns
			);
		}

		dispatch(updateRowColumnsLoadingAction(nextData));
		dispatch(enableSavingChangesStatusAction());

		updatePageEditorLayoutData(nextData, state.segmentsExperienceId)
			.then(() => {
				dispatch(disableSavingChangesStatusAction());
				dispatch(updateLastSaveDateAction());
			})
			.catch(() => {
				dispatch(updateRowColumnsErrorAction(previousData));
				dispatch(disableSavingChangesStatusAction());
			});
	};
}

/**
 * @param {Array} layoutData
 * @return {object}
 * @review
 */
function updateRowColumnsErrorAction(layoutData) {
	return {
		type: UPDATE_ROW_COLUMNS_ERROR,
		value: layoutData
	};
}

/**
 * @param {Array} layoutData
 * @return {object}
 * @review
 */
function updateRowColumnsLoadingAction(layoutData) {
	return {
		type: UPDATE_ROW_COLUMNS_LOADING,
		value: layoutData
	};
}

export {updateRowColumnsAction};
