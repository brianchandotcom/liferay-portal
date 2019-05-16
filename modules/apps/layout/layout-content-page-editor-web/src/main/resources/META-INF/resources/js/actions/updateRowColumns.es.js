import {UPDATE_ROW_COLUMNS_ERROR, UPDATE_ROW_COLUMNS_LOADING, UPDATE_ROW_COLUMNS_SUCCESS} from './actions.es';

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