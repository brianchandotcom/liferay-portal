import {DROP_TARGET_BORDERS} from '../reducers/placeholders.es';

const ARROW_DOWN_KEYCODE = 40;

const ARROW_UP_KEYCODE = 38;

const MOVE_ITEM_DIRECTIONS = {
	DOWN: 1,
	UP: -1
};

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
		row => row.columns.find(
			column => column.fragmentEntryLinkIds.find(
				_fragmentEntryLinkId => (
					_fragmentEntryLinkId === fragmentEntryLinkId
				)
			)
		)
	);
}

/**
 * @param {string} keycode
 * @return {MOVE_ITEM_DIRECTIONS}
 * @review
 */
function getItemMoveDirection(keycode) {
	let direction = null;

	if (keycode === ARROW_UP_KEYCODE) {
		direction = MOVE_ITEM_DIRECTIONS.UP;
	}
	else if (keycode === ARROW_DOWN_KEYCODE) {
		direction = MOVE_ITEM_DIRECTIONS.DOWN;
	}

	return direction;
}

/**
 * Get the fragmentEntryLinkIds of the fragments inside the given section
 * @param {array} structure
 * @param {string} sectionId
 * @return {string[]}
 * @review
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
		row => (row.rowId === sectionId)
	);
}

/**
 * Get target item border from the direction the item is moving in
 * @param {!string} direction
 * @return {DROP_TARGET_BORDERS}
 * @review
 */
function getTargetBorder(direction) {
	let targetBorder = null;

	if (direction === MOVE_ITEM_DIRECTIONS.UP) {
		targetBorder = DROP_TARGET_BORDERS.top;
	}
	else if (direction === MOVE_ITEM_DIRECTIONS.DOWN) {
		targetBorder = DROP_TARGET_BORDERS.bottom;
	}

	return targetBorder;
}

export {
	getColumn,
	getDropSectionPosition,
	getFragmentColumn,
	getFragmentRowIndex,
	getItemMoveDirection,
	getSectionFragmentEntryLinkIds,
	getSectionIndex,
	getTargetBorder
};