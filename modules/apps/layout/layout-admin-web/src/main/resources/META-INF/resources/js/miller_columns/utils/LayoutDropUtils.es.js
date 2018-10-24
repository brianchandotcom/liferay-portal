import {
	appendItemToColumn,
	getColumnActiveItem,
	getItem,
	getItemColumnIndex,
	moveItemInside
} from './LayoutUtils.es';

/**
 * Append an item to a column and calculates newParentPlid and priority
 * @param {object} layoutColumns
 * @param {object} item
 * @param {number} columnIndex
 * @return {object}
 * @review
 */
function dropItemInsideColumn(layoutColumns, item, columnIndex) {
	const nextLayoutColumns = appendItemToColumn(
		item,
		layoutColumns,
		columnIndex
	);

	const newParentPlid = getColumnActiveItem(
		nextLayoutColumns,
		columnIndex - 1
	);

	const priority = layoutColumns[columnIndex].length;

	return {
		layoutColumns: nextLayoutColumns,
		newParentPlid,
		priority
	};
}


/**
 * Inserts an item inside another item's children and
 * calculates new parent plid and priority
 * @param {object} layoutColumns
 * @param {object} item
 * @param {bollean} pathUpdated
 * @param {string} targetItemPlid
 * @return {object}
 * @review
 */
function dropItemInsideItem(
	layoutColumns,
	item,
	pathUpdated,
	targetItemPlid
) {
	let nextLayoutColumns = layoutColumns;
	let priority = null;

	const targetItem = getItem(nextLayoutColumns, targetItemPlid);

	nextLayoutColumns = moveItemInside(
		layoutColumns,
		pathUpdated,
		item,
		targetItem
	);

	if (pathUpdated) {
		const targetColumnIndex = getItemColumnIndex(nextLayoutColumns, targetItemPlid);
		const nextColumn = nextLayoutColumns[targetColumnIndex + 1];

		priority = nextColumn.indexOf(item);
	}

	return {
		layoutColumns: nextLayoutColumns,
		newParentPlid: targetItemPlid,
		priority
	};
}

export {
	dropItemInsideColumn,
	dropItemInsideItem
};