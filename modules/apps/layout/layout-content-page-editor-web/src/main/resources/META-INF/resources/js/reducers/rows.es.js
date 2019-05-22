import {
	ADD_ROW,
	MOVE_ROW,
	REMOVE_ROW,
	UPDATE_ROW_COLUMNS_ERROR,
	UPDATE_ROW_COLUMNS_LOADING,
	UPDATE_ROW_COLUMNS_NUMBER_SUCCESS,
	UPDATE_ROW_CONFIG
} from '../actions/actions.es';
import {
	add,
	addRow,
	remove,
	setIn,
	updateIn,
	updateWidgets
} from '../utils/FragmentsEditorUpdateUtils.es';
import {containsFragmentEntryLinkId} from '../utils/LayoutDataList.es';
import {
	getDropRowPosition,
	getRowFragmentEntryLinkIds,
	getRowIndex
} from '../utils/FragmentsEditorGetUtils.es';
import {
	removeFragmentEntryLinks,
	updatePageEditorLayoutData
} from '../utils/FragmentsEditorFetchUtils.es';

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
		if (action.type === ADD_ROW) {
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
		} else {
			resolve(nextState);
		}
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
		if (action.type === MOVE_ROW) {
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
		} else {
			resolve(nextState);
		}
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

	return new Promise(resolve => {
		if (action.type === REMOVE_ROW) {
			nextState = updateIn(
				nextState,
				['layoutData', 'structure'],
				structure =>
					remove(structure, getRowIndex(structure, action.rowId)),
				[]
			);

			const fragmentEntryLinkIds = getRowFragmentEntryLinkIds(
				state.layoutData.structure[
					getRowIndex(state.layoutData.structure, action.rowId)
				]
			).filter(
				fragmentEntryLinkId =>
					!containsFragmentEntryLinkId(
						nextState.layoutDataList,
						fragmentEntryLinkId,
						nextState.segmentsExperienceId ||
							nextState.defaultSegmentsExperienceId
					)
			);

			fragmentEntryLinkIds.forEach(fragmentEntryLinkId => {
				nextState = updateWidgets(nextState, fragmentEntryLinkId);
			});

			updatePageEditorLayoutData(
				nextState.layoutData,
				nextState.segmentsExperienceId
			)
				.then(() =>
					removeFragmentEntryLinks(
						nextState.layoutData,
						fragmentEntryLinkIds,
						nextState.segmentsExperienceId
					)
				)
				.then(() => {
					resolve(nextState);
				})
				.catch(() => {
					resolve(state);
				});
		} else {
			resolve(state);
		}
	});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {object} action.layoutData
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateRowColumnsReducer(state, action) {
	let nextState = state;

	if (
		action.type === UPDATE_ROW_COLUMNS_ERROR ||
		action.type === UPDATE_ROW_COLUMNS_LOADING
	) {
		nextState = setIn(nextState, ['layoutData'], action.layoutData);
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

	if (action.type === UPDATE_ROW_COLUMNS_NUMBER_SUCCESS) {
		nextState = setIn(nextState, ['layoutData'], action.layoutData);

		action.fragmentEntryLinkIdsToRemove.forEach(fragmentEntryLinkId => {
			nextState = updateWidgets(nextState, fragmentEntryLinkId);
		});
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {object} action.config
 * @param {string} action.rowId
 * @param {string} action.type
 * @return {object}
 */
const updateRowConfigReducer = (state, action) =>
	new Promise(resolve => {
		let nextState = state;

		if (action.type === UPDATE_ROW_CONFIG) {
			const rowIndex = getRowIndex(
				nextState.layoutData.structure,
				action.rowId
			);

			if (rowIndex === -1) {
				resolve(nextState);
			} else {
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

				updatePageEditorLayoutData(
					nextState.layoutData,
					nextState.segmentsExperienceId
				)
					.then(() => {
						resolve(nextState);
					})
					.catch(() => {
						resolve(state);
					});
			}
		} else {
			resolve(nextState);
		}
	});

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
	updateRowColumnsReducer,
	updateRowColumnsNumberReducer,
	updateRowConfigReducer
};
