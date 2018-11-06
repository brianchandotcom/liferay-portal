import {setIn} from '../utils/utils.es';

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

			nextColumnId++;
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