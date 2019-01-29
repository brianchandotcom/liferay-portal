import {ADD_SECTION, MOVE_SECTION, REMOVE_SECTION, UPDATE_SECTION_CONFIG} from '../actions/actions.es';
import {add, remove, setIn, updateIn, updateLayoutData, updateWidgets} from '../utils/FragmentsEditorUpdateUtils.es';
import {getDropSectionPosition, getSectionFragmentEntryLinkIds, getSectionIndex} from '../utils/FragmentsEditorGetUtils.es';

/**
 * @param {!object} state
 * @param {!string} actionType
 * @param {!object} payload
 * @param {!Array} payload.layoutColumns
 * @return {object}
 * @review
 */
function addSectionReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === ADD_SECTION) {
				const position = getDropSectionPosition(
					nextState.layoutData.structure,
					nextState.dropTargetItemId,
					nextState.dropTargetBorder
				);

				const nextData = _addSection(
					payload.layoutColumns,
					nextState.layoutData,
					position
				);

				updateLayoutData(
					nextState.updateLayoutPageTemplateDataURL,
					nextState.portletNamespace,
					nextState.classNameId,
					nextState.classPK,
					nextData
				)
					.then(
						() => {
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
 * @param {!string} payload.sectionId
 * @param {!string} payload.targetBorder
 * @param {!string} payload.targetItemId
 * @return {object}
 * @review
 */
function moveSectionReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === MOVE_SECTION) {
				const nextData = _moveSection(
					payload.sectionId,
					nextState.layoutData,
					payload.targetItemId,
					payload.targetBorder
				);

				updateLayoutData(
					nextState.updateLayoutPageTemplateDataURL,
					nextState.portletNamespace,
					nextState.classNameId,
					nextState.classPK,
					nextData
				)
					.then(
						() => {
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
 * @param {!Array} payload.sectionId
 * @return {object}
 * @review
 */
function removeSectionReducer(state, actionType, payload) {
	let nextState = state;

	return new Promise(
		resolve => {
			if (actionType === REMOVE_SECTION) {
				const nextData = _removeSection(
					nextState.layoutData,
					payload.sectionId
				);

				const fragmentEntryLinkIds = getSectionFragmentEntryLinkIds(
					nextState.layoutData.structure,
					payload.sectionId
				);

				fragmentEntryLinkIds.forEach(
					fragmentEntryLinkId => {
						nextState = updateWidgets(nextState, fragmentEntryLinkId);
					}
				);

				updateLayoutData(
					nextState.updateLayoutPageTemplateDataURL,
					nextState.portletNamespace,
					nextState.classNameId,
					nextState.classPK,
					nextData,
					fragmentEntryLinkIds
				)
					.then(
						() => {
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
 * @param {object} payload.config
 * @param {string} payload.sectionId
 * @return {object}
 */
const updateSectionConfigReducer = (state, actionType, payload) => new Promise(
	resolve => {
		let nextState = state;

		if (actionType === UPDATE_SECTION_CONFIG) {
			const sectionIndex = getSectionIndex(
				nextState.layoutData.structure,
				payload.sectionId
			);

			if (sectionIndex === -1) {
				resolve(nextState);
			}
			else {
				Object.entries(payload.config).forEach(
					entry => {
						const [key, value] = entry;

						const configPath = [
							'layoutData',
							'structure',
							sectionIndex,
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

				updateLayoutData(
					nextState.updateLayoutPageTemplateDataURL,
					nextState.portletNamespace,
					nextState.classNameId,
					nextState.classPK,
					nextState.layoutData
				)
					.then(
						() => {
							resolve(nextState);
						}
					)
					.catch(
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
 * Returns a new layoutData with the given columns inserted as a new section
 * at the given position
 *
 * @param {Array} layoutColumns
 * @param {object} layoutData
 * @param {number} position
 * @return {object}
 */
function _addSection(layoutColumns, layoutData, position) {
	let nextColumnId = layoutData.nextColumnId || 0;
	const nextRowId = layoutData.nextRowId || 0;

	const columns = [];

	layoutColumns.forEach(
		() => {
			columns.push(
				{
					columnId: `${nextColumnId}`,
					fragmentEntryLinkIds: [],
					size: ''
				}
			);

			nextColumnId += 1;
		}
	);

	const nextStructure = add(
		layoutData.structure,
		{
			columns,
			config: {},
			rowId: `${nextRowId}`
		},
		position
	);

	let nextData = setIn(layoutData, ['nextColumnId'], nextColumnId);

	nextData = setIn(nextData, ['structure'], nextStructure);
	nextData = setIn(nextData, ['nextRowId'], nextRowId + 1);

	return nextData;
}

/**
 * Returns a new layoutData with the given section moved to the position
 * calculated with targetItemId and targetItemBorder
 * @param {string} sectionId
 * @param {object} layoutData
 * @param {string} targetItemId
 * @param {string} targetItemBorder
 * @private
 * @return {object}
 * @review
 */
function _moveSection(sectionId, layoutData, targetItemId, targetItemBorder) {
	const index = getSectionIndex(layoutData.structure, sectionId);
	const section = layoutData.structure[index];

	let nextStructure = remove(layoutData.structure, index);

	const position = getDropSectionPosition(
		nextStructure,
		targetItemId,
		targetItemBorder
	);

	nextStructure = add(nextStructure, section, position);

	return setIn(layoutData, ['structure'], nextStructure);
}

/**
 * Returns a new layoutData with the section with the given sectionId removed
 * @param {object} layoutData
 * @param {string} sectionId
 * @return {object}
 * @review
 */
function _removeSection(layoutData, sectionId) {
	const sectionIndex = getSectionIndex(layoutData.structure, sectionId);

	return updateIn(
		layoutData,
		['structure'],
		structure => remove(
			structure,
			sectionIndex
		)
	);
}

export {
	addSectionReducer,
	moveSectionReducer,
	removeSectionReducer,
	updateSectionConfigReducer
};