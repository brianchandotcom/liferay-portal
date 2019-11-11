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
import {
	getDropRowPosition,
	getRowIndex
} from '../utils/FragmentsEditorGetUtils.es';
import {
	add,
	addRow,
	remove,
	setIn,
	updateIn,
	updateWidgets
} from '../utils/FragmentsEditorUpdateUtils.es';

/**
 * @param {object} state
 * @param {object} action
 * @param {Array} action.layoutColumns
 * @param {string} action.type
 * @return {object}
 * @review
 */
function addRowReducer(state, action) {
	let nextState = state;

	return new Promise(resolve => {
		const position = getDropRowPosition(
			nextState.layoutData.structure,
			nextState.dropTargetItemId,
			nextState.dropTargetBorder
		);

		const nextData = addRow(
			action.layoutColumns,
			nextState.layoutData,
			position
		);

		updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId)
			.then(() => {
				nextState = setIn(nextState, ['layoutData'], nextData);

				resolve(nextState);
			})
			.catch(() => {
				resolve(nextState);
			});
	});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.rowId
 * @param {string} action.targetBorder
 * @param {string} action.targetItemId
 * @param {object} action.type
 * @return {object}
 * @review
 */
function moveRowReducer(state, action) {
	let nextState = state;

	return new Promise(resolve => {
		const nextData = _moveRow(
			action.rowId,
			nextState.layoutData,
			action.targetItemId,
			action.targetBorder
		);

		updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId)
			.then(() => {
				nextState = setIn(nextState, ['layoutData'], nextData);

				resolve(nextState);
			})
			.catch(() => {
				resolve(nextState);
			});
	});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.rowId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function removeRowReducer(state, action) {
	let nextState = state;

	const structure = state.layoutData.structure;
	const row = structure.find(row => row.rowId === action.rowId);

	nextState = updateIn(
		nextState,
		['layoutData', 'structure'],
		structure => remove(structure, getRowIndex(structure, action.rowId)),
		[]
	);

	if (row.columns.some(column => column.config && column.config.isDropZone)) {
		nextState = updateIn(
			nextState,
			['layoutData'],
			layoutData => ({...layoutData, hasDropZone: false}),
			{}
		);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {Array} action.fragmentEntryLinkIdsToRemove
 * @param {object} action.layoutData
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateRowColumnsNumberReducer(state, action) {
	let nextState = state;

	nextState = setIn(nextState, ['layoutData'], action.layoutData);

	nextState = updateWidgets(nextState, action.fragmentEntryLinkIdsToRemove);

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {object} action.config
 * @param {string} action.rowId
 * @return {object}
 */
const updateRowConfigReducer = (state, action) => {
	let nextState = state;
	const rowIndex = getRowIndex(nextState.layoutData.structure, action.rowId);

	if (rowIndex !== -1) {
		Object.entries(action.config).forEach(entry => {
			const [key, value] = entry;

			const configPath = [
				'layoutData',
				'structure',
				rowIndex,
				'config',
				key
			];

			nextState = setIn(nextState, configPath, value);
		});
	}

	return nextState;
};

/**
 * Returns a new layoutData with the given row moved to the position
 * calculated with targetItemId and targetItemBorder
 * @param {string} rowId
 * @param {object} layoutData
 * @param {string} targetItemId
 * @param {string} targetItemBorder
 * @private
 * @return {object}
 * @review
 */
function _moveRow(rowId, layoutData, targetItemId, targetItemBorder) {
	const index = getRowIndex(layoutData.structure, rowId);
	const row = layoutData.structure[index];

	let nextStructure = remove(layoutData.structure, index);

	const position = getDropRowPosition(
		nextStructure,
		targetItemId,
		targetItemBorder
	);

	nextStructure = add(nextStructure, row, position);

	return setIn(layoutData, ['structure'], nextStructure);
}

export {
	addRowReducer,
	moveRowReducer,
	removeRowReducer,
	updateRowColumnsNumberReducer,
	updateRowConfigReducer
};
