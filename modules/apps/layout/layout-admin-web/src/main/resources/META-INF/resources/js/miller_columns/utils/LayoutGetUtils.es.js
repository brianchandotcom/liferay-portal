/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * @param {number} columnIndex
 * @param {object} item
 * @param {number} itemColumnIndex
 * @return {boolean} Returns whether a column is child of an item or not
 * @review
 */
function columnIsItemChild(columnIndex, item, itemColumnIndex) {
	return item.active && itemColumnIndex < columnIndex;
}

/**
 * Return the active item of a given column
 * @param {object} layoutColumns
 * @param {number} columnIndex
 * @return {string}
 * @review
 */
function getColumnActiveItem(layoutColumns, columnIndex) {
	const column = layoutColumns[columnIndex];

	const activeItem = column.find(item => item.active);

	return activeItem ? activeItem : null;
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
 * @return {object|null}
 * @review
 */
function getItem(layoutColumns, itemPlid) {
	let item = null;

	layoutColumns.forEach(layoutColumn => {
		item = item || layoutColumn.find(_item => _item.plid === itemPlid);
	});

	return item;
}

/**
 * Get the container column of an item
 * @param {object[]} layoutColumns
 * @param {string} itemPlid
 * @return {object[]|null}
 * @review
 */
function getItemColumn(layoutColumns, itemPlid) {
	let column = null;

	layoutColumns.forEach(layoutColumn => {
		const item = layoutColumn.find(_item => _item.plid === itemPlid);

		if (item) {
			column = layoutColumn;
		}
	});

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

	return parentItem.active && childItemColumnIndex > parentItemColumnIndex;
}

export {
	columnIsItemChild,
	getColumnActiveItem,
	getColumnLastItem,
	getItem,
	getItemColumn,
	getItemColumnIndex,
	itemIsParent
};
