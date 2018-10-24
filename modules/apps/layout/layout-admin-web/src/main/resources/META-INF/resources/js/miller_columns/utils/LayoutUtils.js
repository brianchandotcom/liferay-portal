/**
 * @param {object[]} layoutColumns
 * @param {number} columnIndex
 * @param {string} itemPlid
 * @return {boolean} Returns whether a column is child of an item or not
 * @review
 */
function columnIsItemChild(layoutColumns, columnIndex, itemPlid) {
	let isChild = false;
	const item = getItem(layoutColumns, itemPlid);

	if (item) {
		const itemColumnIndex = getItemColumnIndex(layoutColumns, itemPlid);
		isChild = item.active && (itemColumnIndex < columnIndex);
	}

	return isChild;
}

/**
 * Get last item of a column
 * @param {object[]} layoutColumns
 * @param {number} columnIndex
 * @return {object|undefined} Returns last item or
 * undefined if the column is empty
 * @review
 */
function getColumnLastItem(layoutColumns, columnIndex) {
	const column = layoutColumns[columnIndex];

	return column[column.length - 1];
}

/**
 * Returns an item from its plid
 * @param {object[]} layoutColumns
 * @param {string} itemPlid
 * @private
 * @return {object|undefined}
 * @review
 */

function getItem(layoutColumns, itemPlid) {
	let item;

	layoutColumns.forEach(
		layoutColumn => {
			item = item || layoutColumn.find(
				_item => _item.plid === itemPlid
			);
		}
	);

	return item;
}

/**
 * Get the container column of an item
 * @param {object[]} layoutColumns
 * @param {string} itemPlid
 * @return {object[]|undefined}
 * @review
 */
function getItemColumn(layoutColumns, itemPlid) {
	let column;

	layoutColumns.forEach(
		layoutColumn => {
			const item = layoutColumn.find(
				_item => _item.plid === itemPlid
			);

			if (item) {
				column = layoutColumn;
			}
		}
	);

	return column;
}

/**
 * Return the index of an item's column
 * @param {object[]} layoutColumns
 * @param {string} itemPlid
 * @return {number}
 * @review
 */
function getItemColumnIndex(layoutColumns, itemPlid) {
	const column = getItemColumn(layoutColumns, itemPlid);

	return layoutColumns.indexOf(column);
}

/**
 * @param {object[]} layoutColumns
 * @param {string} childItemPlid
 * @param {string} parentItemPlid
 * @return {boolean} Returns wheter an item is child of a given item
 * @review
 */
function itemIsParent(layoutColumns, childItemPlid, parentItemPlid) {
	const childItemColumnIndex = getItemColumnIndex(
		layoutColumns,
		childItemPlid
	);
	const parentItem = getItem(layoutColumns, parentItemPlid);
	const parentItemColumnIndex = getItemColumnIndex(
		layoutColumns,
		parentItemPlid
	);

	return parentItem.active && (childItemColumnIndex > parentItemColumnIndex);
}