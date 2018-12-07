import {DROP_TARGET_BORDERS} from '../reducers/placeholders.es';

/**
 * Returns the column with the given columnId
 * @param {Object} structure
 * @param {string} columnId
 * @return {Object}
 */
function getColumn(structure, columnId) {
	return structure
		.map(
			section => section.columns.find(
				_column => _column.columnId === columnId
			)
		)
		.filter(column => column)
		.find(column => column);
}

/**
 * Returns the position in the structure of the given section
 * @param {object} structure
 * @param {number} targetSectionId
 * @param {string} targetBorder
 * @return {number}
 */
function getDropSectionPosition(
	structure,
	targetSectionId,
	targetBorder
) {
	let position = structure.length;

	const targetPosition = structure.findIndex(
		section => section.rowId === targetSectionId
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
 * Returns the column that contains the fragment
 * with the given fragmentEntryLinkId.
 *
 * @param {Array} structure
 * @param {string} fragmentEntryLinkId
 * @return {Object}
 */
function getFragmentColumn(structure, fragmentEntryLinkId) {
	return structure
		.map(
			section => section.columns.find(
				_column => _column.fragmentEntryLinkIds.find(
					fragmentId => fragmentId === fragmentEntryLinkId
				)
			)
		)
		.filter(column => column)
		.find(column => column);
}

/**
 * Returns the row index of a given fragmentEntryLinkId.
 * -1 if it is not present.
 *
 * @param {array} structure
 * @param {string} fragmentEntryLinkId
 * @return {number}
 */
function getFragmentRowIndex(structure, fragmentEntryLinkId) {
	return structure.findIndex(
		row => {
			return row.columns.find(
				column => {
					return column.fragmentEntryLinkIds.find(
						_fragmentEntryLinkId => (
							_fragmentEntryLinkId === fragmentEntryLinkId
						)
					);
				}
			);
		}
	);
}

/**
 * Get the fragmentEntryLinkIds of the fragments inside the given section
 * @param {array} structure
 * @param {string} sectionId
 */
function getSectionFragmentEntryLinkIds(structure, sectionId) {
	const section = structure[getSectionIndex(structure, sectionId)];

	let fragmentEntryLinkIds = [];

	section.columns.forEach(
		column => {
			fragmentEntryLinkIds = fragmentEntryLinkIds.concat(
				column.fragmentEntryLinkIds
			);
		}
	);

	return fragmentEntryLinkIds;
}

/**
 * Returns the index of the section with the given rowId
 * @param {array} structure
 * @param {string} sectionId
 * @return {number}
 */
function getSectionIndex(structure, sectionId) {
	return structure.findIndex(
		row => {
			return row.rowId === sectionId;
		}
	);
}

export {
	getColumn,
	getDropSectionPosition,
	getFragmentColumn,
	getFragmentRowIndex,
	getSectionFragmentEntryLinkIds,
	getSectionIndex
};