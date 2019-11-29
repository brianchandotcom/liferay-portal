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

import {DROP_TARGET_BORDERS, DROP_TARGET_ITEM_TYPES} from './LayoutDragDrop.es';
import {
	columnIsItemChild,
	getColumnActiveItem,
	getItemColumn,
	getItemColumnIndex
} from './LayoutGetUtils.es';
import {
	appendItemToColumn,
	moveItemInside,
	removeItem
} from './LayoutUpdateUtils.es';

/**
 * @param {object} layoutColumns
 * @param {object} sourceItem
 * @param {string} sourceItemColumnIndex
 * @param {string} targetId
 * @param {string} targetType
 * @return {boolean} Returns whether a drop is valid or not
 * @review
 */
function dropIsValid(
	layoutColumns,
	sourceItem,
	sourceItemColumnIndex,
	targetId,
	targetType
) {
	let targetColumnHasLayoutAssociated = true;
	let targetColumnIsChild = false;
	let targetEqualsSource = false;

	if (targetType === DROP_TARGET_ITEM_TYPES.column) {
		targetColumnIsChild = columnIsItemChild(
			targetId,
			sourceItem,
			sourceItemColumnIndex
		);

		const sourceColumnHasActiveItem =
			getColumnActiveItem(layoutColumns, sourceItemColumnIndex) !== null;

		const targetColumnHasItems = layoutColumns[targetId].length > 0;

		targetColumnHasLayoutAssociated =
			sourceColumnHasActiveItem || targetColumnHasItems;
	}
	else if (targetType === DROP_TARGET_ITEM_TYPES.item) {
		targetEqualsSource = sourceItem.plid === targetId;
	}

	const targetExists = targetId !== null;

	return (
		targetColumnHasLayoutAssociated &&
		targetExists &&
		!targetEqualsSource &&
		!targetColumnIsChild
	);
}

/**
 * Append an item to a column and calculates newParentPlid and priority
 * @param {object} layoutColumns
 * @param {object} sourceItem
 * @param {number} targetColumnIndex
 * @return {object}
 * @review
 */
function dropItemInsideColumn(layoutColumns, sourceItem, targetColumnIndex) {
	const nextLayoutColumns = appendItemToColumn(
		sourceItem,
		layoutColumns,
		targetColumnIndex
	);

	const newParentPlid = getColumnActiveItem(
		nextLayoutColumns,
		targetColumnIndex - 1
	).plid;

	const priority = layoutColumns[targetColumnIndex].length;

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
 * @param {object} sourceItem
 * @param {number} sourceItemColumnIndex
 * @param {bollean} pathUpdated
 * @param {object} targetItem
 * @return {object}
 * @review
 */
function dropItemInsideItem(
	layoutColumns,
	sourceItem,
	sourceItemColumnIndex,
	pathUpdated,
	targetItem
) {
	let nextLayoutColumns = layoutColumns;
	let priority = null;

	nextLayoutColumns = moveItemInside(
		layoutColumns,
		pathUpdated,
		sourceItem,
		sourceItemColumnIndex,
		targetItem
	);

	if (pathUpdated) {
		const targetColumnIndex = getItemColumnIndex(
			nextLayoutColumns,
			targetItem.plid
		);

		const nextColumn = nextLayoutColumns[targetColumnIndex + 1];

		priority = nextColumn.indexOf(sourceItem);
	}

	return {
		layoutColumns: nextLayoutColumns,
		newParentPlid: targetItem.plid,
		priority
	};
}

/**
 * Insert an item next to another item and
 * calculates new parent plid and priority
 * @param {object} layoutColumns
 * @param {object} sourceItem
 * @param {string} dropPosition
 * @param {object} targetItem
 * @return {object}
 * @review
 */
function dropItemNextToItem(
	layoutColumns,
	sourceItem,
	dropPosition,
	targetItem
) {
	const nextLayoutColumns = removeItem(sourceItem.plid, layoutColumns);

	const targetColumn = getItemColumn(nextLayoutColumns, targetItem.plid);
	const targetColumnIndex = nextLayoutColumns.indexOf(targetColumn);

	const parentPlid = getColumnActiveItem(
		nextLayoutColumns,
		targetColumnIndex - 1
	).plid;

	const targetItemIndex = targetColumn.findIndex(
		targetColumnItem => targetColumnItem.plid === targetItem.plid
	);

	const priority =
		dropPosition === DROP_TARGET_BORDERS.bottom
			? targetItemIndex + 1
			: targetItemIndex;

	const nextTargetColumn = [...targetColumn];

	nextTargetColumn.splice(priority, 0, sourceItem);

	nextLayoutColumns[targetColumnIndex] = nextTargetColumn;

	return {
		layoutColumns: nextLayoutColumns,
		newParentPlid: parentPlid,
		priority
	};
}

export {
	dropIsValid,
	dropItemInsideColumn,
	dropItemInsideItem,
	dropItemNextToItem
};
