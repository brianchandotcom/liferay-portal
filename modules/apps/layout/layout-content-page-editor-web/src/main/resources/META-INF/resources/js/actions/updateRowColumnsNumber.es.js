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
	getRowFragmentEntryLinkIds,
	getRowIndex
} from '../utils/FragmentsEditorGetUtils.es';
import {setIn, updateIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {MAX_COLUMNS} from '../utils/rowConstants';
import {UPDATE_ROW_COLUMNS_NUMBER_SUCCESS} from './actions.es';
import {removeFragmentEntryLinksAction} from './removeFragmentEntryLinks.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from './saveChanges.es';

/**
 * @param {number} numberOfColumns
 * @param {string} rowId
 * @return {function}
 * @review
 */
function updateRowColumnsNumberAction(numberOfColumns, rowId) {
	return function(dispatch, getState) {
		const state = getState();

		const columnsSize = Math.floor(MAX_COLUMNS / numberOfColumns);
		const rowIndex = getRowIndex(state.layoutData.structure, rowId);

		const columns = state.layoutData.structure[rowIndex].columns;

		let fragmentEntryLinkIdsToRemove = [];
		let nextData;

		if (numberOfColumns > columns.length) {
			nextData = _addColumns(
				state.layoutData,
				rowIndex,
				numberOfColumns,
				columnsSize
			);
		}
		else {
			nextData = _removeColumns(
				state.layoutData,
				rowIndex,
				numberOfColumns,
				columnsSize
			);
		}

		if (columns.length > numberOfColumns) {
			fragmentEntryLinkIdsToRemove = getRowFragmentEntryLinkIds({
				columns: columns.slice(numberOfColumns - columns.length)
			});
		}

		dispatch(enableSavingChangesStatusAction());

		updatePageEditorLayoutData(nextData, state.segmentsExperienceId)
			.then(() =>
				dispatch(
					removeFragmentEntryLinksAction(fragmentEntryLinkIdsToRemove)
				)
			)
			.then(() => {
				dispatch(
					updateRowColumnsNumberSuccessAction(
						fragmentEntryLinkIdsToRemove,
						nextData
					)
				);

				dispatch(updateLastSaveDateAction());
				dispatch(disableSavingChangesStatusAction());
			})
			.catch(() => {
				dispatch(disableSavingChangesStatusAction());
			});
	};
}

/**
 * @param {Array} fragmentEntryLinkIdsToRemove
 * @param {object} layoutData
 * @return {object}
 * @review
 */
function updateRowColumnsNumberSuccessAction(
	fragmentEntryLinkIdsToRemove,
	layoutData
) {
	return {
		fragmentEntryLinkIdsToRemove,
		layoutData,
		type: UPDATE_ROW_COLUMNS_NUMBER_SUCCESS
	};
}

/**
 * Returns a new layoutData with the given columns inserted in the specified
 * row with the specified size and resizes the rest of columns to the
 * same size.
 *
 * @param {object} layoutData
 * @param {number} rowIndex
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @return {object}
 */
function _addColumns(layoutData, rowIndex, numberOfColumns, columnsSize) {
	let nextColumnId = layoutData.nextColumnId || 0;

	let nextData = updateIn(
		layoutData,
		['structure', rowIndex, 'columns'],
		columns => {
			const newColumns = columns.map((column, index) =>
				setIn(
					column,
					['size'],
					_getColumnSize(numberOfColumns, columnsSize, index)
				)
			);

			const numberOfNewColumns = numberOfColumns - columns.length;
			const numberOfOldColumns = columns.length;

			for (let i = 0; i < numberOfNewColumns; i++) {
				newColumns.push({
					columnId: `${nextColumnId}`,
					fragmentEntryLinkIds: [],
					size: _getColumnSize(
						numberOfColumns,
						columnsSize,
						i + numberOfOldColumns
					)
				});

				nextColumnId += 1;
			}

			return newColumns;
		}
	);

	nextData = setIn(nextData, ['nextColumnId'], nextColumnId);

	return nextData;
}

/**
 * Returns the new size of a column based on the total number of columns of a
 * grid, the general size and its position in the grid.
 *
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @param {number} columnIndex
 * @return {string}
 */
function _getColumnSize(numberOfColumns, columnsSize, columnIndex) {
	let newColumnSize = columnsSize;

	const middleColumnPosition = Math.ceil(numberOfColumns / 2) - 1;

	if (middleColumnPosition === columnIndex) {
		newColumnSize = MAX_COLUMNS - (numberOfColumns - 1) * columnsSize;
	}

	return newColumnSize.toString();
}

/**
 * Returns a new layoutData without the columns out of range in the specified
 * row and resizes the rest of columns to the specified size.
 *
 * @param {object} layoutData
 * @param {number} rowIndex
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @return {object}
 */
function _removeColumns(layoutData, rowIndex, numberOfColumns, columnsSize) {
	const nextData = updateIn(
		layoutData,
		['structure', rowIndex, 'columns'],
		columns => {
			let newColumns = columns.slice(0, numberOfColumns);

			newColumns = newColumns.map((column, index) =>
				setIn(
					column,
					['size'],
					_getColumnSize(numberOfColumns, columnsSize, index)
				)
			);

			return newColumns;
		}
	);

	return nextData;
}

export {updateRowColumnsNumberAction};
