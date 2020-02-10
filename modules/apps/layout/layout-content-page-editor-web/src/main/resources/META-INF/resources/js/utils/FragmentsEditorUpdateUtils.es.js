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

import {CLEAR_DROP_TARGET, MOVE_ROW} from '../actions/actions.es';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction,
	updateLastSaveDateAction
} from '../actions/saveChanges.es';
import {
	getTargetBorder,
	getWidget,
	getWidgetPath
} from './FragmentsEditorGetUtils.es';
import {
	FRAGMENTS_EDITOR_DRAGGING_CLASS,
	FRAGMENTS_EDITOR_ROW_TYPES
} from './constants';
import {
	DEFAULT_COMPONENT_ROW_CONFIG,
	DEFAULT_SECTION_ROW_CONFIG
} from './rowConstants';

/**
 * Inserts an element in the given position of a given array and returns
 * a copy of the array
 * @param {!Array} array
 * @param {*} element
 * @param {!number} position
 * @return {Array}
 */
function add(array, element, position) {
	const newArray = [...array];

	newArray.splice(position, 0, element);

	return newArray;
}

/**
 * Returns a new layoutData with the given columns inserted as a new row
 * at the given position
 *
 * @param {Array} layoutColumns
 * @param {object} layoutData
 * @param {number} position
 * @param {Array} fragmentEntryLinkIds
 * @param {string} type
 * @return {object}
 */
function addRow(
	layoutColumns,
	layoutData,
	position,
	fragmentEntryLinkIds = [],
	type = FRAGMENTS_EDITOR_ROW_TYPES.componentRow
) {
	let nextColumnId = layoutData.nextColumnId || 0;
	const nextRowId = layoutData.nextRowId || 0;

	const columns = [];

	layoutColumns.forEach(columnSize => {
		columns.push({
			columnId: `${nextColumnId}`,
			fragmentEntryLinkIds,
			size: columnSize
		});

		nextColumnId += 1;
	});

	const defaultConfig =
		type === FRAGMENTS_EDITOR_ROW_TYPES.sectionRow
			? DEFAULT_SECTION_ROW_CONFIG
			: DEFAULT_COMPONENT_ROW_CONFIG;

	const nextStructure = add(
		layoutData.structure,
		{
			columns,
			config: {
				...defaultConfig
			},
			rowId: `${nextRowId}`,
			type
		},
		position
	);

	let nextData = setIn(layoutData, ['nextColumnId'], nextColumnId);

	nextData = setIn(nextData, ['structure'], nextStructure);
	nextData = setIn(nextData, ['nextRowId'], nextRowId + 1);

	return nextData;
}

/**
 * Removes the last key in keyPath from the given object and returns a new one
 * @param {Array|Object} object Original object that will be copied
 * @param {Array<string>} keyPath Array of strings used for reaching the deep property
 * @return {Array|Object} Copy of the original object without the last key in keyPath
 * @review
 */
function deleteIn(object, keyPath) {
	const [lastKey] = keyPath.slice(-1);
	const newKeyPath = keyPath.slice(0, keyPath.length - 1);

	let newObject = Array.isArray(object) ? [...object] : {...object};

	if (keyPath.length === 1) {
		delete newObject[lastKey];
	}
	else {
		newObject = updateIn(object, newKeyPath, lastItem => {
			const newLastItem = Array.isArray(lastItem)
				? [...lastItem]
				: {...lastItem};

			delete newLastItem[lastKey];

			return newLastItem;
		});
	}

	return newObject;
}

/**
 * Dispatches necessary actions to move an item to another position
 * @param {!Object} store Store instance that dispatches the actions
 * @param {!string} moveItemAction
 * @param {!Object} moveItemPayload Data that is passed to the reducer
 * @review
 */
function moveItem(store, moveItemAction, moveItemPayload) {
	store
		.dispatch(enableSavingChangesStatusAction())
		.dispatch({...moveItemPayload, type: moveItemAction})
		.dispatch(updateLastSaveDateAction())
		.dispatch(disableSavingChangesStatusAction())
		.dispatch({
			type: CLEAR_DROP_TARGET
		});
}

/**
 * Moves a row one position in the given direction
 * @param {number} direction
 * @param {number} rowIndex
 * @param {{}} store
 * @param {Array} structure
 * @review
 */
function moveRow(direction, rowIndex, store, structure) {
	const row = structure[rowIndex];
	const targetRow = structure[rowIndex + direction];

	if (targetRow) {
		const moveItemPayload = {
			rowId: row.rowId,
			targetBorder: getTargetBorder(direction),
			targetItemId: targetRow.rowId
		};

		moveItem(store, MOVE_ROW, moveItemPayload);
	}
}

/**
 * Removes from the given array the element in the given position and
 * returns a new array
 * @param {!Array} array
 * @param {!number} position
 * @return {Array}
 * @review
 */
function remove(array, position) {
	const newArray = [...array];

	newArray.splice(position, 1);

	return newArray;
}

/**
 * Dispatches necessary actions to remove an item
 * @param {!Object} store Store instance that dispatches the actions
 * @param {!string} removeItemAction
 * @param {!Object} removeItemPayload Data that is passed to the reducer
 * @review
 */
function removeItem(store, removeAction) {
	store
		.dispatch(enableSavingChangesStatusAction())
		.dispatch(removeAction)
		.dispatch(updateLastSaveDateAction())
		.dispatch(disableSavingChangesStatusAction());
}

/**
 * Set dragging item's position to mouse coordinates
 * @param {MouseEvent} event
 */
function setDraggingItemPosition(event) {
	const draggingElement = document.body.querySelector(
		`.${FRAGMENTS_EDITOR_DRAGGING_CLASS}`
	);

	if (draggingElement instanceof HTMLElement) {
		const newXPos = event.clientX - draggingElement.offsetWidth / 2;
		const newYPos = event.clientY - draggingElement.offsetHeight / 2;

		requestAnimationFrame(() => {
			setElementPosition(draggingElement, newXPos, newYPos);
		});
	}
}

/**
 * Set an element's position to new x and y coordinates
 * @param {HTMLElement} element
 * @param {number} xPos
 * @param {number} yPos
 */
function setElementPosition(element, xPos, yPos) {
	element.style.left = `${xPos}px`;
	element.style.top = `${yPos}px`;
}

/**
 * Recursively inserts a value inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * @param {Array|Object} object Original object that will be copied
 * @param {Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {*} value Value to be inserted
 * @return {Array|Object} Copy of the original object with the new value
 * @review
 */
function setIn(object, keyPath, value) {
	return updateIn(object, keyPath, () => value);
}

/**
 * Recursively inserts the value returned from updater inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * Updater receives the previous value or defaultValue and returns a new value.
 * @param {Array|Object} object Original object that will be copied
 * @param {Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {function} updater Update function
 * @param {*} [defaultValue] Default value to be sent to updater function if
 *  there is no existing value
 * @return {Object}
 * @review
 */
function updateIn(object, keyPath, updater, defaultValue) {
	const [nextKey] = keyPath;
	let target = object;

	if (keyPath.length > 1) {
		target = Array.isArray(target) ? [...target] : {...target};

		target[nextKey] = updateIn(
			target[nextKey] || {},
			keyPath.slice(1),
			updater,
			defaultValue
		);
	}
	else {
		const nextValue =
			typeof target[nextKey] === 'undefined'
				? defaultValue
				: target[nextKey];

		const updatedNextValue = updater(nextValue);

		if (updatedNextValue !== target[nextKey]) {
			target = Array.isArray(target) ? [...target] : {...target};

			target[nextKey] = updatedNextValue;
		}
	}

	return target;
}

/**
 * Sets used widgets based on the portletIds array
 * @param {!Array} widgets
 * @param {{!Array} portletIds
 * @return {Array}
 * @review
 */
function updateUsedWidgets(widgets, portletIds) {
	const filteredWidgets = [...widgets];

	filteredWidgets.forEach(widgetCategory => {
		const {categories = [], portlets = []} = widgetCategory;

		widgetCategory.categories = updateUsedWidgets(categories, portletIds);
		widgetCategory.portlets = portlets.map(portlet => {
			if (
				portletIds.indexOf(portlet.portletId) !== -1 &&
				!portlet.instanceable
			) {
				portlet.used = true;
			}
			else {
				portlet.used = false;
			}

			return portlet;
		});
	});

	return filteredWidgets;
}

/**
 * @param {Object} state
 * @param {Object[]} state.fragmentEntryLinks
 * @param {Object[]} state.widgets
 * @param {string} fragmentEntryLinkId
 * @return {Object}
 */
function updateWidgets(state, fragmentEntryLinkIds = []) {
	let nextState = state;

	fragmentEntryLinkIds.forEach(fragmentEntryLinkId => {
		const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];

		if (fragmentEntryLink && fragmentEntryLink.portletId) {
			const widget = getWidget(
				state.widgets,
				fragmentEntryLink.portletId
			);

			if (!widget.instanceable && widget.used) {
				const widgetPath = getWidgetPath(
					state.widgets,
					fragmentEntryLink.portletId
				);

				nextState = setIn(state, [...widgetPath, 'used'], false);
			}
		}
	});

	return nextState;
}

export {
	add,
	addRow,
	deleteIn,
	moveItem,
	moveRow,
	remove,
	removeItem,
	setDraggingItemPosition,
	setIn,
	updateIn,
	updateUsedWidgets,
	updateWidgets
};
