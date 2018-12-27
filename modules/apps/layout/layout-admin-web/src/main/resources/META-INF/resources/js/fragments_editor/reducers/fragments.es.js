import {
	ADD_FRAGMENT_ENTRY_LINK,
	MOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_EDITABLE_VALUE
} from '../actions/actions.es';
import {DROP_TARGET_BORDERS, DROP_TARGET_ITEM_TYPES} from './placeholders.es';
import {
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../components/fragment_entry_link/FragmentEntryLinkContent.es';
import {
	add,
	remove,
	setIn,
	updateIn,
	updateLayoutData
} from '../utils/FragmentsEditorUpdateUtils.es';
import {
	getColumn,
	getDropSectionPosition,
	getFragmentColumn
} from '../utils/FragmentsEditorGetUtils.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @param {!string} payload.fragmentEntryLinkName
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
					state.addFragmentEntryLinkURL,
					payload.fragmentEntryId,
					payload.fragmentName,
					state.classNameId,
					state.classPK,
					state.portletNamespace
				)
					.then(
						response => {
							fragmentEntryLink = response;

							nextData = _addFragment(
								fragmentEntryLink.fragmentEntryLinkId,
								state.dropTargetBorder,
								state.dropTargetItemId,
								state.dropTargetItemType,
								state.layoutData
							);

							return updateLayoutData(
								state.updateLayoutPageTemplateDataURL,
								state.portletNamespace,
								state.classNameId,
								state.classPK,
								nextData
							);
						}
					)
					.then(
						() => _getFragmentEntryLinkContent(
							state.renderFragmentEntryURL,
							fragmentEntryLink,
							state.portletNamespace
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
					state.layoutData,
					payload.fragmentEntryLinkId
				);

				nextData = _addFragment(
					payload.fragmentEntryLinkId,
					payload.targetBorder,
					payload.targetItemId,
					payload.targetItemType,
					nextData
				);

				_moveFragmentEntryLink(
					state.updateLayoutPageTemplateDataURL,
					state.portletNamespace,
					state.classNameId,
					state.classPK,
					nextData
				)
					.then(
						response => {
							if (response.error) {
								throw response.error;
							}

							nextState = setIn(
								state,
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
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!string} payload.fragmentEntryLinkId
 * @return {object}
 * @review
 */
function removeFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === REMOVE_FRAGMENT_ENTRY_LINK) {
				const {fragmentEntryLinkId} = payload;

				let nextData = setIn(
					state.layoutData,
					['structure'],
					[...state.layoutData.structure]
				);

				nextData = _removeFragment(nextData, fragmentEntryLinkId);

				_removeFragmentEntryLink(
					state.deleteFragmentEntryLinkURL,
					state.portletNamespace,
					state.classNameId,
					state.classPK,
					fragmentEntryLinkId,
					nextData
				)
					.then(
						() => {
							nextState = setIn(nextState, ['layoutData'], nextData);

							delete nextState.fragmentEntryLinks[
								payload.fragmentEntryLinkId
							];

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
					editableValueId
				} = payload;

				const {editableValues} = state.fragmentEntryLinks[payload.fragmentEntryLinkId];

				const nextEditableValues = setIn(
					editableValues,
					[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
						editableId,
						editableValueId
					],
					editableValue
				);

				const formData = new FormData();

				formData.append(
					`${state.portletNamespace}fragmentEntryLinkId`,
					payload.fragmentEntryLinkId
				);

				formData.append(
					`${state.portletNamespace}editableValues`,
					JSON.stringify(nextEditableValues)
				);

				fetch(
					state.editFragmentEntryLinkURL,
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

/**
 * Adds a fragment at the corresponding container in the layout
 * @param {string} fragmentEntryLinkId
 * @param {string} dropTargetBorder
 * @param {string} dropTargetItemId
 * @param {string} dropTargetItemType
 * @param {object} layoutData
 * @private
 * @return {object}
 * @review
 */
function _addFragment(
	fragmentEntryLinkId,
	dropTargetBorder,
	dropTargetItemId,
	dropTargetItemType,
	layoutData
) {
	let nextData = layoutData;

	if (dropTargetItemType === DROP_TARGET_ITEM_TYPES.column) {
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
	else if (dropTargetItemType === DROP_TARGET_ITEM_TYPES.fragment) {
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
	else if (dropTargetItemType === DROP_TARGET_ITEM_TYPES.section) {
		const position = getDropSectionPosition(
			layoutData.structure,
			dropTargetItemId,
			dropTargetBorder
		);

		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			position
		);
	}
	else {
		nextData = _addSingleFragmentRow(
			layoutData,
			fragmentEntryLinkId,
			layoutData.structure.length
		);
	}

	return nextData;
}

/**
 * @param {string} addFragmentEntryLinkURL
 * @param {string} fragmentEntryId
 * @param {string} fragmentName
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} portletNamespace
 * @return {object}
 * @review
 */
function _addFragmentEntryLink(
	addFragmentEntryLinkURL,
	fragmentEntryId,
	fragmentName,
	classNameId,
	classPK,
	portletNamespace
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}fragmentId`, fragmentEntryId);
	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

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
					fragmentEntryId,
					fragmentEntryLinkId: response.fragmentEntryLinkId,
					name: fragmentName
				};
			}
		);
}

/**
 * @param {string[]} fragmentEntryLinkIds
 * @param {string} targetFragmentEntryLinkId
 * @param {DROP_TARGET_BORDERS} targetBorder
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
		if (targetBorder === DROP_TARGET_BORDERS.top) {
			position = targetPosition;
		}
		else {
			position = targetPosition + 1;
		}
	}

	return position;
}

/**
 * @param {string} renderFragmentEntryURL
 * @param {{fragmentEntryLinkId: string}} fragmentEntryLink
 * @param {string} portletNamespace
 * @return {Promise<object>}
 * @review
 */
function _getFragmentEntryLinkContent(
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

				return Object.assign(
					{},
					fragmentEntryLink,
					{
						content: response.content
					}
				);
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
	const section = structure.find(
		_section => _section.columns.find(
			_column => column === _column
		)
	);

	const columnIndex = section.columns.indexOf(column);
	const sectionIndex = structure.indexOf(section);

	return updateIn(
		layoutData,
		[
			'structure',
			sectionIndex,
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
 * @param {number} position
 * @return {object}
 */
function _addSingleFragmentRow(layoutData, fragmentEntryLinkId, position) {
	const nextColumnId = layoutData.nextColumnId || 0;
	const nextRowId = layoutData.nextRowId || 0;
	const nextStructure = [...layoutData.structure];

	nextStructure.splice(
		position,
		0,
		{
			columns: [
				{
					columnId: `${nextColumnId}`,
					fragmentEntryLinkIds: [fragmentEntryLinkId],
					size: '12'
				}
			],
			rowId: `${nextRowId}`
		}
	);

	let nextData = setIn(layoutData, ['structure'], nextStructure);

	nextData = setIn(nextData, ['nextColumnId'], nextColumnId + 1);
	nextData = setIn(nextData, ['nextRowId'], nextRowId + 1);

	return nextData;
}

/**
 * @param {string} moveFragmentEntryLinkURL
 * @param {string} portletNamespace
 * @param {string} classNameId
 * @param {string} classPK
 * @param {object} layoutData
 * @return {Promise}
 * @review
 */
function _moveFragmentEntryLink(
	moveFragmentEntryLinkURL,
	portletNamespace,
	classNameId,
	classPK,
	layoutData
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);
	formData.append(`${portletNamespace}data`, JSON.stringify(layoutData));

	return fetch(
		moveFragmentEntryLinkURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

/**
 * @param {object} layoutData
 * @param {string} fragmentEntryLinkId
 * @return {Promise}
 * @review
 */
function _removeFragment(layoutData, fragmentEntryLinkId) {
	const {structure} = layoutData;

	const column = getFragmentColumn(structure, fragmentEntryLinkId);
	const section = structure.find(
		_section => _section.columns.find(
			_column => column === _column
		)
	);

	const columnIndex = section.columns.indexOf(column);
	const fragmentIndex = column.fragmentEntryLinkIds.indexOf(
		fragmentEntryLinkId
	);
	const sectionIndex = structure.indexOf(section);

	return updateIn(
		layoutData,
		[
			'structure',
			sectionIndex,
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

/**
 * @param {string} deleteFragmentEntryLinkURL
 * @param {string} portletNamespace
 * @param {string} classNameId
 * @param {string} classPK
 * @param {string} fragmentEntryLinkId
 * @param {object} layoutData
 * @return {Promise}
 * @review
 */
function _removeFragmentEntryLink(
	deleteFragmentEntryLinkURL,
	portletNamespace,
	classNameId,
	classPK,
	fragmentEntryLinkId,
	layoutData
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);
	formData.append(`${portletNamespace}data`, JSON.stringify(layoutData));

	formData.append(
		`${portletNamespace}fragmentEntryLinkId`,
		fragmentEntryLinkId
	);

	return fetch(
		deleteFragmentEntryLinkURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

export {
	addFragmentEntryLinkReducer,
	moveFragmentEntryLinkReducer,
	removeFragmentEntryLinkReducer,
	updateEditableValueReducer
};