import {
	appendItemToColumn,
	getColumnActiveItem
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

export {dropItemInsideColumn};