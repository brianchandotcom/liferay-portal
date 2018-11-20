import {
	ADD_FRAGMENT_ENTRY_LINK,
	MOVE_FRAGMENT_ENTRY_LINK,
	REMOVE_FRAGMENT_ENTRY_LINK,
	UPDATE_EDITABLE_VALUE
} from '../actions/actions.es';
import {DRAG_POSITIONS} from './placeholders.es';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../components/fragment_entry_link/FragmentEntryLink.es';
import {
	add,
	getFragmentRowIndex,
	setIn,
	updateIn,
	updateLayoutData
} from '../utils/utils.es';

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

							const position = _getDropFragmentPosition(
								state.layoutData.structure,
								state.hoveredElementId,
								state.hoveredElementBorder
							);

							nextData = _addSingleFragmentRow(
								state.layoutData,
								fragmentEntryLink.fragmentEntryLinkId,
								position
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
						() => {
							return _getFragmentEntryLinkContent(
								state.renderFragmentEntryURL,
								fragmentEntryLink,
								state.portletNamespace
							);
						}
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
 * @param {!string} payload.originFragmentEntryLinkId
 * @param {!string} payload.originFragmentEntryLinkBorder
 * @param {!string} payload.targetFragmentEntryLinkId
 * @return {object}
 * @review
 */
function moveFragmentEntryLinkReducer(state, actionType, payload) {
	return new Promise(
		resolve => {
			let nextState = state;

			if (actionType === MOVE_FRAGMENT_ENTRY_LINK) {
				const border = payload.targetFragmentEntryLinkBorder;
				const originId = payload.originFragmentEntryLinkId;
				const targetId = payload.targetFragmentEntryLinkId;

				const nextData = setIn(
					state.layoutData,
					['structure'],
					[...state.layoutData.structure]
				);

				const originIndex = getFragmentRowIndex(
					nextData.structure,
					originId
				);

				const originContent = nextData.structure[originIndex];

				nextData.structure.splice(originIndex, 1);

				let targetIndex = getFragmentRowIndex(
					nextData.structure,
					targetId
				);

				if (border !== DRAG_POSITIONS.top) {
					targetIndex += 1;
				}

				nextData.structure.splice(targetIndex, 0, originContent);

				_moveFragmentEntryLink(
					state.updateLayoutPageTemplateDataURL,
					state.portletNamespace,
					state.classNameId,
					state.classPK,
					nextData
				).then(
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

				const nextData = setIn(
					state.layoutData,
					['structure'],
					[...state.layoutData.structure]
				);

				const index = getFragmentRowIndex(
					nextData.structure,
					fragmentEntryLinkId
				);

				nextData.structure.splice(index, 1);

				_removeFragmentEntryLink(
					state.deleteFragmentEntryLinkURL,
					state.portletNamespace,
					state.classNameId,
					state.classPK,
					fragmentEntryLinkId,
					nextData
				).then(
					() => {
						nextState = setIn(nextState, ['layoutData'], nextData);

						delete nextState.fragmentEntryLinks[
							payload.fragmentEntryLinkId
						];

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
	).then(
		response => {
			return response.json();
		}
	).then(
		response => {
			if (!response.fragmentEntryLinkId) {
				throw new Error();
			}

			return {
				config: {},
				content: '',
				editableValues: JSON.parse(response.editableValues),
				fragmentEntryId: fragmentEntryId,
				fragmentEntryLinkId: response.fragmentEntryLinkId,
				name: fragmentName
			};
		}
	);
}

function _getDropFragmentPosition(
	structure,
	targetFragmentEntryLinkId,
	targetBorder
) {
	let position = structure.length;

	const targetPosition = getFragmentRowIndex(
		structure,
		targetFragmentEntryLinkId
	);

	if (targetPosition > -1 && targetBorder) {
		if (targetBorder === DRAG_POSITIONS.top) {
			position = targetPosition;
		}
		else {
			position = targetPosition + 1;
		}
	}

	return position;
}

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
	).then(
		response => response.json()
	).then(
		response => {
			if (!response.content) {
				throw new Error();
			}

			return Object.assign(
				{},
				fragmentEntryLink,
				{content: response.content}
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
		section => section.columns.find(
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