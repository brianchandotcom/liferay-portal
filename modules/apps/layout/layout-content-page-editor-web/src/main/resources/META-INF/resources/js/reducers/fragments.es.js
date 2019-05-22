import {
	ADD_FRAGMENT_ENTRY_LINK,
	CLEAR_FRAGMENT_EDITOR,
	DISABLE_FRAGMENT_EDITOR,
	ENABLE_FRAGMENT_EDITOR,
	MOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_CONFIG_ATTRIBUTES,
	UPDATE_EDITABLE_VALUE_ERROR,
	UPDATE_EDITABLE_VALUE_LOADING
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
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
	FRAGMENTS_EDITOR_ITEM_BORDERS,
	FRAGMENTS_EDITOR_ITEM_TYPES,
	FRAGMENTS_EDITOR_ROW_TYPES
} from '../utils/constants';
import {
	getColumn,
	getDropRowPosition,
	getFragmentColumn,
	getFragmentRowIndex
} from '../utils/FragmentsEditorGetUtils.es';
import {
	removeFragmentEntryLinks,
	updatePageEditorLayoutData
} from '../utils/FragmentsEditorFetchUtils.es';

/**
 * Adds a fragment at the corresponding container in the layout
 * @param {string} fragmentEntryLinkId
 * @param {string} dropTargetBorder
 * @param {string} dropTargetItemId
 * @param {string} dropTargetItemType
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkRowType
 * @private
 * @return {object}
 * @review
 */
function addFragment(
	fragmentEntryLinkId,
	dropTargetBorder,
	dropTargetItemId,
	dropTargetItemType,
	layoutData,
	fragmentEntryLinkRowType = FRAGMENTS_EDITOR_ROW_TYPES.componentRow
) {
	let nextData = layoutData;

	if (dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.column) {
		const fragmentColumn = getColumn(
			layoutData.structure,
			dropTargetItemId
		);

		nextData = _addFragmentToColumn(
			layoutData,
			fragmentEntryLinkId,
			dropTargetItemId,
			fragmentColumn.fragmentEntryLinkIds.length
		);
	} else if (dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
		const fragmentColumn = getFragmentColumn(
			layoutData.structure,
			dropTargetItemId
		);

		const position = _getDropFragmentPosition(
			fragmentColumn.fragmentEntryLinkIds,
			dropTargetItemId,
			dropTargetBorder
		);

		nextData = _addFragmentToColumn(
			layoutData,
			fragmentEntryLinkId,
			fragmentColumn.columnId,
			position
		);
	} else if (dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row) {
		const position = getDropRowPosition(
			layoutData.structure,
			dropTargetItemId,
			dropTargetBorder
		);

		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			fragmentEntryLinkRowType,
			position
		);
	} else {
		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			fragmentEntryLinkRowType,
			layoutData.structure.length
		);
	}

	return nextData;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @param {string} action.fragmentEntryKey
 * @param {string} action.fragmentEntryLinkType
 * @param {string} action.fragmentName
 * @return {object}
 * @review
 */
function addFragmentEntryLinkReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		if (action.type === ADD_FRAGMENT_ENTRY_LINK) {
			let fragmentEntryLink = null;
			let nextData = null;

			_addFragmentEntryLink(
				nextState.addFragmentEntryLinkURL,
				action.fragmentEntryKey,
				action.fragmentName,
				nextState.classNameId,
				nextState.classPK,
				nextState.portletNamespace,
				nextState.segmentsExperienceId
			)
				.then(response => {
					fragmentEntryLink = response;

					nextData = addFragment(
						fragmentEntryLink.fragmentEntryLinkId,
						nextState.dropTargetBorder,
						nextState.dropTargetItemId,
						nextState.dropTargetItemType,
						nextState.layoutData,
						action.fragmentEntryLinkRowType
					);

					return updatePageEditorLayoutData(
						nextData,
						nextState.segmentsExperienceId
					);
				})
				.then(() =>
					getFragmentEntryLinkContent(
						nextState.renderFragmentEntryURL,
						fragmentEntryLink,
						nextState.portletNamespace
					)
				)
				.then(response => {
					fragmentEntryLink = response;

					nextState = setIn(
						nextState,
						[
							'fragmentEntryLinks',
							fragmentEntryLink.fragmentEntryLinkId
						],
						fragmentEntryLink
					);

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
 * @param {string} action.itemId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function clearFragmentEditorReducer(state, action) {
	let nextState = state;

	if (action.type === CLEAR_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorClear'], action.itemId);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @return {object}
 * @review
 */
function disableFragmentEditorReducer(state, action) {
	let nextState = state;

	if (action.type === DISABLE_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorEnabled'], null);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.type
 * @param {string} action.itemId
 * @return {object}
 * @review
 */
function enableFragmentEditorReducer(state, action) {
	let nextState = state;

	if (action.type === ENABLE_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorEnabled'], action.itemId);
	}

	return nextState;
}

/**
 * @param {string} renderFragmentEntryURL
 * @param {{fragmentEntryLinkId: string}} fragmentEntryLink
 * @param {string} portletNamespace
 * @return {Promise<object>}
 * @review
 */
function getFragmentEntryLinkContent(
	renderFragmentEntryURL,
	fragmentEntryLink,
	portletNamespace
) {
	const formData = new FormData();

	formData.append(
		`${portletNamespace}fragmentEntryLinkId`,
		fragmentEntryLink.fragmentEntryLinkId
	);

	return fetch(renderFragmentEntryURL, {
		body: formData,
		credentials: 'include',
		method: 'POST'
	})
		.then(response => response.json())
		.then(response => {
			if (!response.content) {
				throw new Error();
			}

			fragmentEntryLink = setIn(
				fragmentEntryLink,
				['content'],
				response.content
			);

			return setIn(fragmentEntryLink, ['error'], response.error);
		});
}

/**
 * @param {object} state
 * @param {object} action
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.fragmentEntryLinkType
 * @param {string} action.targetBorder
 * @param {string} action.targetItemId
 * @param {string} action.targetItemType
 * @param {string} action.type
 * @return {object}
 * @review
 */
function moveFragmentEntryLinkReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		if (action.type === MOVE_FRAGMENT_ENTRY_LINK) {
			let nextData = null;

			nextData = _removeFragment(
				nextState.layoutData,
				action.fragmentEntryLinkId,
				action.fragmentEntryLinkRowType
			);

			nextData = addFragment(
				action.fragmentEntryLinkId,
				action.targetBorder,
				action.targetItemId,
				action.targetItemType,
				nextData,
				action.fragmentEntryLinkRowType
			);

			updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId)
				.then(response => {
					if (response.error) {
						throw response.error;
					}

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
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.fragmentEntryLinkRowType
 * @param {string} action.type
 * @return {Promise<object>}
 * @review
 */
function removeFragmentEntryLinkReducer(state, action) {
	return new Promise(resolve => {
		let nextState = state;

		if (action.type === REMOVE_FRAGMENT_ENTRY_LINK) {
			const {fragmentEntryLinkId} = action;

			const fragmentEntryLinkRow =
				nextState.layoutData.structure[
					getFragmentRowIndex(
						nextState.layoutData.structure,
						fragmentEntryLinkId
					)
				];

			nextState = setIn(
				nextState,
				['layoutData'],
				_removeFragment(
					nextState.layoutData,
					fragmentEntryLinkId,
					fragmentEntryLinkRow.type ||
						FRAGMENTS_EDITOR_ROW_TYPES.componentRow
				)
			);

			nextState = updateWidgets(nextState, action.fragmentEntryLinkId);

			const _shouldRemoveFragmentEntryLink = !containsFragmentEntryLinkId(
				nextState.layoutDataList,
				fragmentEntryLinkId,
				nextState.segmentsExperienceId ||
					nextState.defaultSegmentsExperienceId
			);

			let updateLayoutDataPromise = updatePageEditorLayoutData(
				nextState.layoutData,
				nextState.segmentsExperienceId
			);

			if (_shouldRemoveFragmentEntryLink) {
				nextState = updateIn(
					nextState,
					['fragmentEntryLinks'],
					fragmentEntryLinks => {
						const nextFragmentEntryLinks = Object.assign(
							{},
							fragmentEntryLinks
						);

						delete nextFragmentEntryLinks[fragmentEntryLinkId];

						return nextFragmentEntryLinks;
					},
					{}
				);

				updateLayoutDataPromise = updateLayoutDataPromise.then(() =>
					removeFragmentEntryLinks(
						nextState.layoutData,
						[fragmentEntryLinkId],
						nextState.segmentsExperienceId
					)
				);
			}

			updateLayoutDataPromise
				.then(() => {
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
 * @param {string} action.type
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.editableValues
 * @param {Date} action.date
 * @return {object}
 * @review
 */
function updateEditableValueReducer(state, action) {
	let nextState = state;

	if (
		action.type === UPDATE_EDITABLE_VALUE_ERROR ||
		action.type === UPDATE_EDITABLE_VALUE_LOADING
	) {
		nextState = setIn(
			nextState,
			[
				'fragmentEntryLinks',
				action.fragmentEntryLinkId,
				'editableValues'
			],
			action.editableValues
		);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {object} action
 * @param {object} action.config
 * @param {string} action.editableId
 * @param {string} action.fragmentEntryLinkId
 * @param {string} action.type
 * @return {object}
 * @review
 */
function updateFragmentEntryLinkConfigReducer(state, action) {
	let nextState = state;

	return new Promise(resolve => {
		if (action.type === UPDATE_CONFIG_ATTRIBUTES) {
			const {config, editableId, fragmentEntryLinkId} = action;

			let {editableValues} = nextState.fragmentEntryLinks[
				fragmentEntryLinkId
			];

			Object.entries(config).forEach(entry => {
				const [key, value] = entry;

				const keysTreeArray = [
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					editableId,
					'config',
					key
				];

				editableValues = setIn(editableValues, keysTreeArray, value);
			});

			const formData = new FormData();

			formData.append(
				`${nextState.portletNamespace}fragmentEntryLinkId`,
				fragmentEntryLinkId
			);

			formData.append(
				`${nextState.portletNamespace}editableValues`,
				JSON.stringify(editableValues)
			);

			fetch(nextState.editFragmentEntryLinkURL, {
				body: formData,
				credentials: 'include',
				method: 'POST'
			}).then(() => {
				nextState = setIn(
					nextState,
					[
						'fragmentEntryLinks',
						fragmentEntryLinkId,
						'editableValues'
					],
					editableValues
				);

				resolve(nextState);
			});
		} else {
			resolve(nextState);
		}
	});
}

/**
 * @param {string} addFragmentEntryLinkURL
 * @param {string} fragmentEntryKey
 * @param {string} fragmentName
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} portletNamespace
 * @return {object}
 * @review
 */
function _addFragmentEntryLink(
	addFragmentEntryLinkURL,
	fragmentEntryKey,
	fragmentName,
	classNameId,
	classPK,
	portletNamespace,
	segmentsExperienceId
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}fragmentKey`, fragmentEntryKey);
	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);
	formData.append(
		`${portletNamespace}segmentsExperienceId`,
		segmentsExperienceId
	);

	return fetch(addFragmentEntryLinkURL, {
		body: formData,
		credentials: 'include',
		method: 'POST'
	})
		.then(response => response.json())
		.then(response => {
			if (!response.fragmentEntryLinkId) {
				throw new Error();
			}

			return {
				config: {},
				content: '',
				editableValues: JSON.parse(response.editableValues),
				fragmentEntryKey,
				fragmentEntryLinkId: response.fragmentEntryLinkId,
				name: fragmentName
			};
		});
}

/**
 * Returns a new layoutData with the given fragmentEntryLinkId inserted
 * into a given column at the given position
 *
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @param {string} targetColumnId
 * @param {number} position
 * @return {object}
 */
function _addFragmentToColumn(
	layoutData,
	fragmentEntryLinkId,
	targetColumnId,
	position
) {
	const {structure} = layoutData;

	const column = getColumn(structure, targetColumnId);
	const row = structure.find(_row =>
		_row.columns.find(_column => column === _column)
	);

	const columnIndex = row.columns.indexOf(column);
	const rowIndex = structure.indexOf(row);

	return updateIn(
		layoutData,
		['structure', rowIndex, 'columns', columnIndex, 'fragmentEntryLinkIds'],
		fragmentEntryLinkIds =>
			add(fragmentEntryLinkIds, fragmentEntryLinkId, position)
	);
}

/**
 * Returns a new layoutData with the given fragmentEntryLinkId inserted
 * into a single-column new row. The row will be created at the given position.
 *
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @param {string} fragmentEntryLinkRowType
 * @param {number} position
 * @return {object}
 */
function _addSingleFragmentRow(
	layoutData,
	fragmentEntryLinkId,
	fragmentEntryLinkRowType,
	position
) {
	return addRow(
		['12'],
		layoutData,
		position,
		[fragmentEntryLinkId],
		fragmentEntryLinkRowType
	);
}

/**
 * @param {string[]} fragmentEntryLinkIds
 * @param {string} targetFragmentEntryLinkId
 * @param {FRAGMENTS_EDITOR_ITEM_BORDERS} targetBorder
 * @return {number}
 * @review
 */
function _getDropFragmentPosition(
	fragmentEntryLinkIds,
	targetFragmentEntryLinkId,
	targetBorder
) {
	let position = fragmentEntryLinkIds.length;

	const targetPosition = fragmentEntryLinkIds.indexOf(
		targetFragmentEntryLinkId
	);

	if (targetPosition > -1 && targetBorder) {
		if (targetBorder === FRAGMENTS_EDITOR_ITEM_BORDERS.top) {
			position = targetPosition;
		} else {
			position = targetPosition + 1;
		}
	}

	return position;
}

/**
 * Removes a given fragmentEntryLinkId from a given layoutData.
 * It does not remove it from fragmentEntryLinks array.
 *
 * If the fragmentEntryLinkRowType is "section", it will remove the whole
 * row (and columns) too.
 *
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @param {string} [fragmentEntryLinkRowType=FRAGMENTS_EDITOR_ROW_TYPES.componentRow]
 * @return {object} Next layout data
 * @review
 */
function _removeFragment(
	layoutData,
	fragmentEntryLinkId,
	fragmentEntryLinkRowType = FRAGMENTS_EDITOR_ROW_TYPES.componentRow
) {
	const {structure} = layoutData;

	const column = getFragmentColumn(structure, fragmentEntryLinkId);
	const row = structure.find(_row =>
		_row.columns.find(_column => column === _column)
	);

	const columnIndex = row.columns.indexOf(column);
	const fragmentIndex = column.fragmentEntryLinkIds.indexOf(
		fragmentEntryLinkId
	);
	const rowIndex = structure.indexOf(row);

	let nextData = null;

	if (fragmentEntryLinkRowType === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow) {
		nextData = updateIn(layoutData, ['structure'], structure =>
			remove(structure, rowIndex)
		);
	} else {
		nextData = updateIn(
			layoutData,
			[
				'structure',
				rowIndex,
				'columns',
				columnIndex,
				'fragmentEntryLinkIds'
			],
			fragmentEntryLinkIds => remove(fragmentEntryLinkIds, fragmentIndex)
		);
	}

	return nextData;
}

export {
	addFragment,
	addFragmentEntryLinkReducer,
	clearFragmentEditorReducer,
	disableFragmentEditorReducer,
	enableFragmentEditorReducer,
	getFragmentEntryLinkContent,
	moveFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer,
	updateEditableValueReducer,
	updateFragmentEntryLinkConfigReducer
};
