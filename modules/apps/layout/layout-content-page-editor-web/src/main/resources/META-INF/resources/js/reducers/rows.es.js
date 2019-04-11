import {ADD_ROW, MOVE_ROW, REMOVE_ROW, UPDATE_ROW_COLUMNS, UPDATE_ROW_COLUMNS_NUMBER, UPDATE_ROW_CONFIG} from '../actions/actions.es';
import {add, addRow, remove, setIn, updateIn, updateWidgets} from '../utils/FragmentsEditorUpdateUtils.es';
import {containsFragmentEntryLinkId} from '../utils/LayoutDataList.es';
import {getDropRowPosition, getRowFragmentEntryLinkIds, getRowIndex} from '../utils/FragmentsEditorGetUtils.es';
import {MAX_COLUMNS} from '../utils/rowConstants';
import {removeFragmentEntryLinks, updatePageEditorLayoutData} from '../utils/FragmentsEditorFetchUtils.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!Array} payload.layoutColumns
 * @return {object}
 * @review
 */
function addRowReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === ADD_ROW) {
				const position = getDropRowPosition(
					nextState.layoutData.structure,
					nextState.dropTargetItemId,
					nextState.dropTargetBorder
				);

				const nextData = addRow(
					payload.layoutColumns,
					nextState.layoutData,
					position
				);

				updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId).then(
					() => {
						nextState = setIn(
							nextState,
							['layoutData'],
							nextData
						);

						resolve(nextState);
					}
				).catch(
					() => {
						resolve(nextState);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.rowId
 * @param {!string} payload.targetBorder
 * @param {!string} payload.targetItemId
 * @return {object}
 * @review
 */
function moveRowReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === MOVE_ROW) {
				const nextData = _moveRow(
					payload.rowId,
					nextState.layoutData,
					payload.targetItemId,
					payload.targetBorder
				);

				updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId).then(
					() => {
						nextState = setIn(
							nextState,
							['layoutData'],
							nextData
						);

						resolve(nextState);
					}
				).catch(
					() => {
						resolve(nextState);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!Array} payload.rowId
 * @return {object}
 * @review
 */
function removeRowReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === REMOVE_ROW) {
				const nextData = _removeRow(
					nextState.layoutData,
					payload.rowId
				);

				const row = nextState.layoutData.structure[getRowIndex(nextState.layoutData.structure, payload.rowId)];

				const fragmentEntryLinkIds = getRowFragmentEntryLinkIds(
					row
				);

				const fragmentsToRemove = fragmentEntryLinkIds.filter(
					id => !containsFragmentEntryLinkId(
						nextState.layoutDataList,
						id,
						nextState.segmentsExperienceId
					)
				);

				fragmentsToRemove.forEach(
					fragmentEntryLinkId => {
						nextState = updateWidgets(nextState, fragmentEntryLinkId);
					}
				);

				updatePageEditorLayoutData(
					nextData,
					nextState.segmentsExperienceId
				).then(
					() => removeFragmentEntryLinks(
						fragmentsToRemove,
						nextState.segmentsExperienceId
					)
				).then(
					() => {
						nextState = setIn(
							nextState,
							['layoutData'],
							nextData
						);

						resolve(nextState);
					}
				).catch(
					() => {
						resolve(nextState);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!Array} payload.columns
 * @param {!string} payload.rowId
 * @return {object}
 * @review
 */
const updateRowColumnsReducer = (state, actionType, payload) => new Promise(
	resolve => {
		let nextState = state;

		if (actionType === UPDATE_ROW_COLUMNS) {
			const rowIndex = getRowIndex(
				nextState.layoutData.structure,
				payload.rowId
			);

			if (rowIndex === -1) {
				resolve(nextState);
			}
			else {
				const nextData = setIn(
					nextState.layoutData,
					[
						'structure',
						rowIndex,
						'columns'
					],
					payload.columns
				);

				updatePageEditorLayoutData(
					nextData,
					nextState.segmentsExperienceId
				).then(
					() => {
						nextState = setIn(
							nextState,
							['layoutData'],
							nextData
						);

						resolve(nextState);
					}
				).catch(
					() => {
						resolve(nextState);
					}
				);
			}
		}
		else {
			resolve(nextState);
		}
	}
);

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!Array} payload.rowId
 * @return {object}
 * @review
 */
function updateRowColumnsNumberReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === UPDATE_ROW_COLUMNS_NUMBER) {

				let fragmentEntryLinkIdsToRemove = [];
				let nextData;

				const numberOfColumns = payload.numberOfColumns;

				const columnsSize = Math.floor(MAX_COLUMNS / numberOfColumns);
				const rowIndex = getRowIndex(nextState.layoutData.structure, payload.rowId);

				let columns = nextState.layoutData.structure[rowIndex].columns;

				if (numberOfColumns > columns.length) {
					nextData = _addColumns(nextState.layoutData, rowIndex, numberOfColumns, columnsSize);
				}
				else {
					let fragmentEntryLinkIdsToRemove = getRowFragmentEntryLinkIds(
						{
							columns: columns.slice(numberOfColumns - columns.length)
						}
					);

					fragmentEntryLinkIdsToRemove.forEach(
						fragmentEntryLinkId => {
							nextState = updateWidgets(nextState, fragmentEntryLinkId);
						}
					);

					nextData = _removeColumns(nextState.layoutData, rowIndex, numberOfColumns, columnsSize);
				}

				updatePageEditorLayoutData(
					nextData,
					nextState.segmentsExperienceId
				).then(
					() => removeFragmentEntryLinks(
						fragmentEntryLinkIdsToRemove,
						nextState.segmentsExperienceId
					)
				).then(
					() => {
						nextState = setIn(
							nextState,
							['layoutData'],
							nextData
						);

						resolve(nextState);
					}
				).catch(
					() => {
						resolve(nextState);
					}
				);
			}
			else {
				resolve(nextState);
			}
		}
	);
}

/**
 * @param {object} state
 * @param {string} actionType
 * @param {object} payload
 * @param {object} payload.config
 * @param {string} payload.rowId
 * @return {object}
 */
const updateRowConfigReducer = (state, actionType, payload) => new Promise(
	resolve => {
		let nextState = state;

		if (actionType === UPDATE_ROW_CONFIG) {
			const rowIndex = getRowIndex(
				nextState.layoutData.structure,
				payload.rowId
			);

			if (rowIndex === -1) {
				resolve(nextState);
			}
			else {
				Object.entries(payload.config).forEach(
					entry => {
						const [key, value] = entry;

						const configPath = [
							'layoutData',
							'structure',
							rowIndex,
							'config',
							key
						];

						nextState = setIn(
							nextState,
							configPath,
							value
						);
					}
				);

				updatePageEditorLayoutData(
					nextState.layoutData,
					nextState.segmentsExperienceId
				).then(
					() => {
						resolve(nextState);
					}
				).catch(
					() => {
						resolve(state);
					}
				);
			}
		}
		else {
			resolve(nextState);
		}
	}
);

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
			columns.forEach(
				(column, index) => {
					column.size = _getColumnSize(numberOfColumns, columnsSize, index);
				}
			);

			const numberOfNewColumns = numberOfColumns - columns.length;
			const numberOfOldColumns = columns.length;

			for (let i = 0; i < numberOfNewColumns; i++) {
				columns.push(
					{
						columnId: `${nextColumnId}`,
						fragmentEntryLinkIds: [],
						size: _getColumnSize(numberOfColumns, columnsSize, (i + numberOfOldColumns))
					}
				);

				nextColumnId += 1;
			}

			return columns;
		}
	);

	nextData = setIn(layoutData, ['nextColumnId'], nextColumnId);

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
		newColumnSize = MAX_COLUMNS - ((numberOfColumns - 1) * columnsSize);
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
	let nextData = updateIn(
		layoutData,
		['structure', rowIndex, 'columns'],
		columns => {
			columns = columns.slice(0, numberOfColumns);

			columns.forEach(
				(column, index) => {
					column.size = _getColumnSize(numberOfColumns, columnsSize, index);
				}
			);

			return columns;
		}
	);

	return nextData;
}

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

/**
 * Returns a new layoutData with the row with the given rowId removed
 * @param {object} layoutData
 * @param {string} rowId
 * @return {object}
 * @review
 */
function _removeRow(layoutData, rowId) {
	const rowIndex = getRowIndex(layoutData.structure, rowId);

	return updateIn(
		layoutData,
		['structure'],
		structure => remove(
			structure,
			rowIndex
		)
	);
}

export {
	addRowReducer,
	moveRowReducer,
	removeRowReducer,
	updateRowColumnsReducer,
	updateRowColumnsNumberReducer,
	updateRowConfigReducer
};