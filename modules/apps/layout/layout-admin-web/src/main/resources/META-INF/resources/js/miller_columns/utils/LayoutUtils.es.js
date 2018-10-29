import {setIn} from '../../utils/utils.es';
import {DROP_TARGET_TYPES} from './LayoutDragDrop.es';

/**
 * Append an item to a column and returns a new array of columns
 * @param {object} sourceItem
 * @param {object[]} layoutColumns
 * @param {number} targetColumnIndex
 * @return {object[]}
 * @review
 */
function appendItemToColumn(sourceItem, layoutColumns, targetColumnIndex) {
	let nextLayoutColumns = layoutColumns;

	if (sourceItem) {
		nextLayoutColumns = removeItem(sourceItem.plid, nextLayoutColumns);

		const nextTargetColumn = [...nextLayoutColumns[targetColumnIndex]];

		nextTargetColumn.splice(nextTargetColumn.length, 0, sourceItem);

		nextLayoutColumns[targetColumnIndex] = nextTargetColumn;
	}

	return nextLayoutColumns;
}

/**
 * Removes following columns starting at position indicated
 * by startColumnIndex and returns a new array of columns
 * @param {object} layoutColumns
 * @param {number} startColumnIndex
 * @return {object}
 * @review
 */
function clearFollowingColumns(layoutColumns, startColumnIndex) {
	const nextLayoutColumns = layoutColumns.map(
		(layoutColumn) => [...layoutColumn]
	);

	for (let i = startColumnIndex + 1; i < nextLayoutColumns.length; i++) {
		nextLayoutColumns[i] = [];
	}

	return nextLayoutColumns;
}

/**
 * Clears path if an active item is moved to another column
 * @param {object} layoutColumns
 * @param {object} sourceItem
 * @param {number} sourceItemColumnIndex
 * @param {string} targetItemPlid
 * @return {object}
 * @review
 */
function clearPath(
	layoutColumns,
	sourceItem,
	sourceItemColumnIndex,
	targetItemPlid
) {
	let nextLayoutColumns = layoutColumns.map(
		(layoutColumn) => [...layoutColumn]
	);

	const targetColumnIndex = getItemColumnIndex(
		nextLayoutColumns,
		targetItemPlid
	);

	if (
		sourceItem &&
		sourceItem.active &&
		(sourceItemColumnIndex !== targetColumnIndex)
	) {
		sourceItem.active = false;

		nextLayoutColumns = clearFollowingColumns(
			nextLayoutColumns,
			sourceItemColumnIndex
		);

		nextLayoutColumns = deleteEmptyColumns(nextLayoutColumns);
	}

	return nextLayoutColumns;
}

/**
 * @param {number} columnIndex
 * @param {object} item
 * @param {number} itemColumnIndex
 * @return {boolean} Returns whether a column is child of an item or not
 * @review
 */
function columnIsItemChild(columnIndex, item, itemColumnIndex) {
	return item.active && (itemColumnIndex < columnIndex);
}

/**
 * Removes extra empty columns when there are more than three and returns
 * a new Array with the columns removed.
 * @param {object} layoutColumns
 * @private
 * @return {object}
 * @review
 */
function deleteEmptyColumns(layoutColumns) {
	const nextLayoutColumns = [...layoutColumns];

	for (let i = 3; (i < nextLayoutColumns.length) &&
		(nextLayoutColumns[i].length === 0); i++) {
		nextLayoutColumns.splice(i, 1);
	}

	return nextLayoutColumns;
}

/**
 * @param {object} sourceItem
 * @param {string} sourceItemColumnIndex
 * @param {string} targetId
 * @param {string} targetType
 * @return {boolean} Returns whether a drop is valid or not
 * @review
 */
function dropIsValid(
	sourceItem,
	sourceItemColumnIndex,
	targetId,
	targetType
) {
	let targetColumnIsChild = false;
	let targetEqualsSource = false;

	if (targetType === DROP_TARGET_TYPES.column) {
		targetColumnIsChild = columnIsItemChild(
			targetId,
			sourceItem,
			sourceItemColumnIndex
		);
	}
	else if (targetType === DROP_TARGET_TYPES.item) {
		targetEqualsSource = (sourceItem.plid === targetId);
	}

	const targetExists = (targetId !== null);

	return targetExists && !targetEqualsSource && !targetColumnIsChild;
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

	const activeItem = column.find(
		(item) => item.active
	);

	return (
		activeItem ? activeItem : null
	);
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
 * Returns the Home item of an array of columns
 * @param {object[]} layoutColumns
 * @private
 * @return {object|null}
 * @review
 */
function getHomeItem(layoutColumns) {
	let item = null;

	layoutColumns.forEach(
		(layoutColumn) => {
			item = item || layoutColumn.find(
				(_item) => _item.homePage
			);
		}
	);

	return item;
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

	layoutColumns.forEach(
		(layoutColumn) => {
			item = item || layoutColumn.find(
				(_item) => _item.plid === itemPlid
			);
		}
	);

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

	layoutColumns.forEach(
		(layoutColumn) => {
			const item = layoutColumn.find(
				(_item) => _item.plid === itemPlid
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


/**
 * Insert an item inside another item's children
 * and returns a new array of columns
 * @param {object} layoutColumns
 * @param {boolean} pathUpdated
 * @param {object} sourceItem
 * @param {number} sourceItemColumnIndex
 * @param {string} targetItemPlid
 * @return {object}
 * @review
 */
function moveItemInside(
	layoutColumns,
	pathUpdated,
	sourceItem,
	sourceItemColumnIndex,
	targetItem
) {
	let nextLayoutColumns = removeItem(sourceItem.plid, layoutColumns);

	const targetColumn = getItemColumn(
		nextLayoutColumns,
		targetItem.plid
	);

	const targetColumnIndex = getItemColumnIndex(
		nextLayoutColumns,
		targetItem.plid
	);

	if (targetItem.active) {
		const nextColumn = nextLayoutColumns[targetColumnIndex + 1];

		if (nextColumn) {
			nextLayoutColumns = setIn(
				nextLayoutColumns,
				[
					targetColumnIndex + 1,
					nextColumn.length
				],
				sourceItem
			);
		}
		else {
			nextLayoutColumns = setIn(
				nextLayoutColumns,
				[targetColumnIndex + 1],
				[]
			);

			nextLayoutColumns = setIn(
				nextLayoutColumns,
				[
					targetColumnIndex + 1,
					0
				],
				sourceItem
			);
		}
	}

	if (sourceItem.active && !pathUpdated) {
		nextLayoutColumns = clearFollowingColumns(
			nextLayoutColumns,
			sourceItemColumnIndex
		);

		nextLayoutColumns = deleteEmptyColumns(nextLayoutColumns);
	}

	return setIn(
		nextLayoutColumns,
		[
			targetColumnIndex,
			targetColumn.indexOf(targetItem),
			'hasChild'
		],
		true
	);
}


/**
 * Removes an item, if any, from a column and returns a new array of columns
 * @param {string} itemPlid
 * @param {object[]} layoutColumns
 * @param {string} targetItemPlid
 * @return {object[]} new column array
 * @review
 */
function removeItem(itemPlid, layoutColumns) {
	const item = getItem(layoutColumns, itemPlid);
	let nextLayoutColumns = layoutColumns;

	if (item) {
		nextLayoutColumns = [...layoutColumns];

		const itemColumn = getItemColumn(nextLayoutColumns, itemPlid);

		if (itemColumn) {
			const itemIndex = itemColumn.findIndex(
				(_item) => _item.plid === itemPlid
			);
			const nextItemColumn = [...itemColumn];
			const nextItemColumnIndex = getItemColumnIndex(
				nextLayoutColumns,
				itemPlid
			);

			nextItemColumn.splice(itemIndex, 1);
			nextLayoutColumns[nextItemColumnIndex] = nextItemColumn;
		}
	}

	return nextLayoutColumns;
}

/**
 * Set the first page as Home page
 * @param {!Array} layoutColumns
 * @param {string} currentHomeItemPlid
 * @return {object|null}
 * @review
 */
function setHomePage(layoutColumns) {
	let nextLayoutColumns = layoutColumns;

	const firstItem = nextLayoutColumns[1][0];

	if (!firstItem.homePage) {
		const currentHomeItem = getHomeItem(layoutColumns);
		const currentHomeItemIndex = nextLayoutColumns[1].findIndex(
			(item) => item.plid === currentHomeItem.plid
		);

		nextLayoutColumns = setIn(nextLayoutColumns, [1, 0, 'homePage'], true);

		nextLayoutColumns = setIn(
			nextLayoutColumns,
			[
				1,
				currentHomeItemIndex,
				'homePage'
			],
			false
		);
	}

	return nextLayoutColumns;
}

export {
	appendItemToColumn,
	clearFollowingColumns,
	clearPath,
	columnIsItemChild,
	deleteEmptyColumns,
	dropIsValid,
	getColumnActiveItem,
	getColumnLastItem,
	getItem,
	getItemColumn,
	getItemColumnIndex,
	itemIsParent,
	moveItemInside,
	removeItem,
	setHomePage
};