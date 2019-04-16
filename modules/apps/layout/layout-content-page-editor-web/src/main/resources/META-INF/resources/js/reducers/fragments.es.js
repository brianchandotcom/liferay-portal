import {ADD_FRAGMENT_ENTRY_LINK, CLEAR_FRAGMENT_EDITOR, DISABLE_FRAGMENT_EDITOR, ENABLE_FRAGMENT_EDITOR, MOVE_FRAGMENT_ENTRY_LINK, REMOVE_FRAGMENT_ENTRY_LINK, UPDATE_CONFIG_ATTRIBUTES, UPDATE_EDITABLE_VALUE} from '../actions/actions.es';
import {add, addRow, remove, setIn, updateIn, updateWidgets} from '../utils/FragmentsEditorUpdateUtils.es';
import {containsFragmentEntryLinkId} from '../utils/LayoutDataList.es';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../components/fragment_entry_link/FragmentEntryLinkContent.es';
import {FRAGMENT_ENTRY_LINK_TYPES, FRAGMENTS_EDITOR_ITEM_BORDERS, FRAGMENTS_EDITOR_ITEM_TYPES, FRAGMENTS_EDITOR_ROW_TYPES} from '../utils/constants';
import {getColumn, getDropRowPosition, getFragmentColumn, getFragmentRowIndex} from '../utils/FragmentsEditorGetUtils.es';
import {removeFragmentEntryLinks, updatePageEditorLayoutData} from '../utils/FragmentsEditorFetchUtils.es';

/**
 * Adds a fragment at the corresponding container in the layout
 * @param {string} fragmentEntryLinkId
 * @param {string} dropTargetBorder
 * @param {string} dropTargetItemId
 * @param {string} dropTargetItemType
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkType
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
	fragmentEntryLinkType = FRAGMENT_ENTRY_LINK_TYPES.component
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
	}
	else if (dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
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
	}
	else if (dropTargetItemType === FRAGMENTS_EDITOR_ITEM_TYPES.row) {
		const position = getDropRowPosition(
			layoutData.structure,
			dropTargetItemId,
			dropTargetBorder
		);

		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			fragmentEntryLinkType,
			position
		);
	}
	else {
		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			fragmentEntryLinkType,
			layoutData.structure.length
		);
	}

	return nextData;
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryKey
 * @param {!string} payload.fragmentName
 * @return {object}
 * @review
 */
function addFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === ADD_FRAGMENT_ENTRY_LINK) {
				let fragmentEntryLink = null;
				let nextData = null;

				_addFragmentEntryLink(
					nextState.addFragmentEntryLinkURL,
					payload.fragmentEntryKey,
					payload.fragmentName,
					nextState.classNameId,
					nextState.classPK,
					nextState.portletNamespace,
					nextState.segmentsExperienceId
				)
					.then(
						response => {
							fragmentEntryLink = response;

							nextData = addFragment(
								fragmentEntryLink.fragmentEntryLinkId,
								nextState.dropTargetBorder,
								nextState.dropTargetItemId,
								nextState.dropTargetItemType,
								nextState.layoutData,
								payload.fragmentEntryLinkType
							);

							return updatePageEditorLayoutData(
								nextData,
								nextState.segmentsExperienceId
							);
						}
					)
					.then(
						() => getFragmentEntryLinkContent(
							nextState.renderFragmentEntryURL,
							fragmentEntryLink,
							nextState.portletNamespace
						)
					)
					.then(
						response => {
							fragmentEntryLink = response;

							nextState = setIn(
								nextState,
								[
									'fragmentEntryLinks',
									fragmentEntryLink.fragmentEntryLinkId
								],
								fragmentEntryLink
							);

							nextState = setIn(
								nextState,
								['layoutData'],
								nextData
							);

							resolve(nextState);
						}
					)
					.catch(
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
 * @param {string} payload.itemId
 * @return {object}
 * @review
 */
function clearFragmentEditorReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === CLEAR_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorClear'], payload.itemId);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {string} actionType
 * @return {object}
 * @review
 */
function disableFragmentEditorReducer(state, actionType) {
	let nextState = state;

	if (actionType === DISABLE_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorEnabled'], null);
	}

	return nextState;
}

/**
 * @param {object} state
 * @param {string} actionType
 * @param {object} payload
 * @param {string} payload.itemId
 * @return {object}
 * @review
 */
function enableFragmentEditorReducer(state, actionType, payload) {
	let nextState = state;

	if (actionType === ENABLE_FRAGMENT_EDITOR) {
		nextState = setIn(nextState, ['fragmentEditorEnabled'], payload.itemId);
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

	return fetch(
		renderFragmentEntryURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	)
		.then(
			response => response.json()
		)
		.then(
			response => {
				if (!response.content) {
					throw new Error();
				}

				fragmentEntryLink = setIn(
					fragmentEntryLink,
					['content'],
					response.content
				);

				return setIn(
					fragmentEntryLink,
					['error'],
					response.error
				);
			}
		);
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @param {!string} payload.targetBorder
 * @param {!string} payload.targetItemId
 * @param {!string} payload.targetItemType
 * @return {object}
 * @review
 */
function moveFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === MOVE_FRAGMENT_ENTRY_LINK) {
				let nextData = null;

				nextData = _removeFragment(
					nextState.layoutData,
					payload.fragmentEntryLinkId,
					payload.fragmentEntryLinkType
				);

				nextData = addFragment(
					payload.fragmentEntryLinkId,
					payload.targetBorder,
					payload.targetItemId,
					payload.targetItemType,
					nextData,
					payload.fragmentEntryLinkType
				);

				updatePageEditorLayoutData(nextData, nextState.segmentsExperienceId).then(
					response => {
						if (response.error) {
							throw response.error;
						}

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
 * @param {string} payload.fragmentEntryLinkId
 * @param {string} payload.fragmentEntryLinkType
 * @return {Promise<object>}
 * @review
 */
function removeFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === REMOVE_FRAGMENT_ENTRY_LINK) {
				const {fragmentEntryLinkId} = payload;

				let nextData = setIn(
					nextState.layoutData,
					['structure'],
					[...nextState.layoutData.structure]
				);

				const fragmentEntryLinkRow = nextData.structure[
					getFragmentRowIndex(
						nextData.structure,
						fragmentEntryLinkId
					)
				];

				let fragmentEntryLinkType = FRAGMENT_ENTRY_LINK_TYPES.component;

				if (fragmentEntryLinkRow.type === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow) {
					fragmentEntryLinkType = FRAGMENT_ENTRY_LINK_TYPES.section;
				}

				nextData = _removeFragment(
					nextData,
					fragmentEntryLinkId,
					fragmentEntryLinkType
				);

				const _shouldRemoveFragmentEntryLink = !containsFragmentEntryLinkId(
					nextState.layoutDataList,
					fragmentEntryLinkId,
					nextState.segmentsExperienceId
				);

				nextState = setIn(nextState, ['layoutData'], nextData);

				if (_shouldRemoveFragmentEntryLink) {
					removeFragmentEntryLinks(
						nextState.layoutData,
						[fragmentEntryLinkId],
						nextState.segmentsExperienceId
					).then(
						() => {
							nextState = updateWidgets(
								nextState,
								payload.fragmentEntryLinkId
							);

							nextState.setIn(
								nextState,
								['fragmentEntryLinks'],
								nextState.fragmentEntryLinks.filter(
									_fragmentEntryLink => {
										return _fragmentEntryLink.fragmentEntryLinkId !==
											payload.fragmentEntryLinkId;
									}
								)
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
					updatePageEditorLayoutData(
						nextData,
						nextState.segmentsExperienceId
					).then(
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
}

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {object} payload
 * @param {string} payload.fragmentEntryLinkId
 * @param {string} payload.editableId
 * @param {string} payload.editableValue
 * @param {string} payload.editableValueId
 * @return {object}
 * @review
 */
function updateEditableValueReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === UPDATE_EDITABLE_VALUE) {
				const {
					editableId,
					editableValue,
					editableValueId,
					editableValueSegmentsExperienceId
				} = payload;

				const {editableValues} = nextState.fragmentEntryLinks[payload.fragmentEntryLinkId];

				const keysTreeArray = editableValueSegmentsExperienceId ? [
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					editableId,
					editableValueSegmentsExperienceId
				] : [
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					editableId
				];

				let nextEditableValues = setIn(
					editableValues,
					[...keysTreeArray, editableValueId],
					editableValue
				);

				if (editableValueId === 'mappedField') {
					nextEditableValues = updateIn(
						nextEditableValues,
						keysTreeArray,
						editableValue => {
							const nextEditableValue = Object.assign({}, editableValue);

							[
								'config',
								state.defaultSegmentsEntryId,
								...Object.keys(state.availableLanguages),
								...Object.keys(state.availableSegmentsEntries)
							].forEach(
								key => {
									delete nextEditableValue[key];
								}
							);

							return nextEditableValue;
						}
					);
				}

				const formData = new FormData();

				formData.append(
					`${nextState.portletNamespace}fragmentEntryLinkId`,
					payload.fragmentEntryLinkId
				);

				formData.append(
					`${nextState.portletNamespace}editableValues`,
					JSON.stringify(nextEditableValues)
				);

				fetch(
					nextState.editFragmentEntryLinkURL,
					{
						body: formData,
						credentials: 'include',
						method: 'POST'
					}
				).then(
					() => {
						nextState = setIn(
							nextState,
							[
								'fragmentEntryLinks',
								payload.fragmentEntryLinkId,
								'editableValues'
							],
							nextEditableValues
						);

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

function updateFragmentEntryLinkConfigReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === UPDATE_CONFIG_ATTRIBUTES) {
				const {
					config,
					editableId,
					fragmentEntryLinkId
				} = payload;

				let {editableValues} = nextState.fragmentEntryLinks[fragmentEntryLinkId];

				Object.entries(config).forEach(
					entry => {
						const [key, value] = entry;

						const keysTreeArray = [
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
							editableId,
							'config',
							key
						];

						editableValues = setIn(
							editableValues,
							keysTreeArray,
							value
						);
					}
				);

				const formData = new FormData();

				formData.append(
					`${nextState.portletNamespace}fragmentEntryLinkId`,
					fragmentEntryLinkId
				);

				formData.append(
					`${nextState.portletNamespace}editableValues`,
					JSON.stringify(editableValues)
				);

				fetch(
					nextState.editFragmentEntryLinkURL,
					{
						body: formData,
						credentials: 'include',
						method: 'POST'
					}
				).then(
					() => {
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
	formData.append(`${portletNamespace}segmentsExperienceId`, segmentsExperienceId);

	return fetch(
		addFragmentEntryLinkURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	)
		.then(
			response => response.json()
		)
		.then(
			response => {
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
			}
		);
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
	const row = structure.find(
		_row => _row.columns.find(
			_column => column === _column
		)
	);

	const columnIndex = row.columns.indexOf(column);
	const rowIndex = structure.indexOf(row);

	return updateIn(
		layoutData,
		[
			'structure',
			rowIndex,
			'columns',
			columnIndex,
			'fragmentEntryLinkIds'
		],
		fragmentEntryLinkIds => add(
			fragmentEntryLinkIds,
			fragmentEntryLinkId,
			position
		)
	);
}

/**
 * Returns a new layoutData with the given fragmentEntryLinkId inserted
 * into a single-column new row. The row will be created at the given position.
 *
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @param {string} fragmentEntryLinkType
 * @param {number} position
 * @return {object}
 */
function _addSingleFragmentRow(
	layoutData,
	fragmentEntryLinkId,
	fragmentEntryLinkType,
	position
) {
	const rowType = fragmentEntryLinkType === FRAGMENT_ENTRY_LINK_TYPES.section ?
		FRAGMENTS_EDITOR_ROW_TYPES.sectionRow :
		FRAGMENTS_EDITOR_ROW_TYPES.componentRow;

	return addRow(
		['12'],
		layoutData,
		position,
		[fragmentEntryLinkId],
		rowType
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
		}
		else {
			position = targetPosition + 1;
		}
	}

	return position;
}

/**
 * Removes a given fragmentEntryLinkId from a given layoutData.
 * It does not remove it from fragmentEntryLinks array.
 *
 * If the fragmentEntryLinkType is "section", it will remove the whole
 * row (and columns) too.
 *
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @param {string} [fragmentEntryLinkType=FRAGMENTS_EDITOR_ROW_TYPES.componentRow]
 * @return {object} Next layout data
 * @review
 */
function _removeFragment(
	layoutData,
	fragmentEntryLinkId,
	fragmentEntryLinkType = FRAGMENTS_EDITOR_ROW_TYPES.componentRow
) {
	const {structure} = layoutData;

	const column = getFragmentColumn(structure, fragmentEntryLinkId);
	const row = structure.find(
		_row => _row.columns.find(
			_column => column === _column
		)
	);

	const columnIndex = row.columns.indexOf(column);
	const fragmentIndex = column.fragmentEntryLinkIds.indexOf(
		fragmentEntryLinkId
	);
	const rowIndex = structure.indexOf(row);

	let nextData = null;

	if (fragmentEntryLinkType === FRAGMENT_ENTRY_LINK_TYPES.section) {
		nextData = updateIn(
			layoutData,
			['structure'],
			structure => remove(
				structure,
				rowIndex
			)
		);
	}
	else {
		nextData = updateIn(
			layoutData,
			[
				'structure',
				rowIndex,
				'columns',
				columnIndex,
				'fragmentEntryLinkIds'
			],
			fragmentEntryLinkIds => remove(
				fragmentEntryLinkIds,
				fragmentIndex
			)
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