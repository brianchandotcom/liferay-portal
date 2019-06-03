import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from './saveChanges.es';
import {getRowIndex} from '../utils/FragmentsEditorGetUtils.es';
import {setIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	UPDATE_ROW_COLUMNS_ERROR,
	UPDATE_ROW_COLUMNS_LOADING,
	UPDATE_ROW_COLUMNS_SUCCESS
} from './actions.es';
import {updatePageEditorLayoutData} from '../utils/FragmentsEditorFetchUtils.es';

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
				dispatch(updateRowColumnsSuccessAction());
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
		layoutData,
		type: UPDATE_ROW_COLUMNS_ERROR
	};
}

/**
 * @param {Array} layoutData
 * @return {object}
 * @review
 */
function updateRowColumnsLoadingAction(layoutData) {
	return {
		layoutData,
		type: UPDATE_ROW_COLUMNS_LOADING
	};
}

/**
 * @return {object}
 * @review
 */
function updateRowColumnsSuccessAction() {
	return {
		type: UPDATE_ROW_COLUMNS_SUCCESS
	};
}

export {updateRowColumnsAction};
