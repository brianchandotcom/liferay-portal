import {
	appendItemToColumn,
	columnIsItemChild,
	getColumnActiveItem,
	getItemColumn,
	getItemColumnIndex,
	moveItemInside,
	removeItem
} from './LayoutUtils.es';
import {DRAG_POSITIONS, DROP_TARGET_TYPES} from './LayoutDragDrop.es';

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
function dropItemNextToItem(layoutColumns, sourceItem, dropPosition, targetItem) {
	const nextLayoutColumns = removeItem(sourceItem.plid, layoutColumns);

	const targetColumn = getItemColumn(nextLayoutColumns, targetItem.plid);
	const targetColumnIndex = nextLayoutColumns.indexOf(targetColumn);

	const parentPlid = getColumnActiveItem(nextLayoutColumns, targetColumnIndex - 1).plid;

	const targetItemIndex = targetColumn.findIndex(
		(targetColumnItem) => targetColumnItem.plid === targetItem.plid
	);

	const priority = (dropPosition === DRAG_POSITIONS.bottom) ?
		(targetItemIndex + 1) : targetItemIndex;

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