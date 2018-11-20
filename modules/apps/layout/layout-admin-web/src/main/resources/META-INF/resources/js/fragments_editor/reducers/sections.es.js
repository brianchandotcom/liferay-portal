import {ADD_SECTION} from '../actions/actions.es';
import {
	getDropSectionPosition,
	setIn,
	updateLayoutData
} from '../utils/utils.es';

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
					state.layoutData.structure,
					state.hoveredElementId,
					state.hoveredElementBorder
				);

				const nextData = _addSection(
					payload.layoutColumns,
					state.layoutData,
					position
				);

				updateLayoutData(
					state.updateLayoutPageTemplateDataURL,
					state.portletNamespace,
					state.classNameId,
					state.classPK,
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
	const nextStructure = [...layoutData.structure];

	const columns = [];

	layoutColumns.forEach(
		element => {
			columns.push(
				{
					columnId: `${nextColumnId}`,
					fragmentEntryLinkIds: [],
					size: `${element}`
				}
			);

			nextColumnId += 1;
		}
	);

	nextStructure.splice(
		position,
		0,
		{
			columns,
			rowId: `${nextRowId}`
		}
	);

	let nextData = setIn(layoutData, ['nextColumnId'], nextColumnId);

	nextData = setIn(nextData, ['structure'], nextStructure);
	nextData = setIn(nextData, ['nextRowId'], nextRowId + 1);

	return nextData;
}

export {addSectionReducer};