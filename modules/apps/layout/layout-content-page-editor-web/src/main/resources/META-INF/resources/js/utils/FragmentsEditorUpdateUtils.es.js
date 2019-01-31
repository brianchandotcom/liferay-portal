import {contains} from 'metal-dom';

import {CLEAR_ACTIVE_ITEM, CLEAR_DROP_TARGET, CLEAR_HOVERED_ITEM, UPDATE_LAST_SAVE_DATE, UPDATE_SAVING_CHANGES_STATUS} from '../actions/actions.es';
import {FRAGMENTS_EDITOR_ITEM_TYPES} from '../utils/constants';
import {getWidget, getWidgetPath} from './FragmentsEditorGetUtils.es';

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
 * @param {string} itemId
 * @param {FRAGMENTS_EDITOR_ITEM_TYPES} itemType
 * @review
 */
function focusItem(itemId, itemType) {
	if (itemId && itemType) {
		let attr = '';

		if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.section) {
			attr = 'data-layout-section-id';
		}
		else if (itemType === FRAGMENTS_EDITOR_ITEM_TYPES.fragment) {
			attr = 'data-fragment-entry-link-id';
		}

		const item = document.querySelector(`[${attr}='${itemId}']`);

		if (item) {
			item.focus();
		}
	}
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
		.dispatchAction(
			UPDATE_SAVING_CHANGES_STATUS,
			{
				savingChanges: true
			}
		)
		.dispatchAction(
			moveItemAction,
			moveItemPayload
		)
		.dispatchAction(
			UPDATE_LAST_SAVE_DATE,
			{
				lastSaveDate: new Date()
			}
		)
		.dispatchAction(
			UPDATE_SAVING_CHANGES_STATUS,
			{
				savingChanges: false
			}
		)
		.dispatchAction(
			CLEAR_DROP_TARGET
		)
		.dispatchAction(
			CLEAR_HOVERED_ITEM
		);
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
function removeItem(store, removeItemAction, removeItemPayload) {
	store
		.dispatchAction(
			UPDATE_SAVING_CHANGES_STATUS,
			{
				savingChanges: true
			}
		)
		.dispatchAction(
			removeItemAction,
			removeItemPayload
		)
		.dispatchAction(
			UPDATE_LAST_SAVE_DATE,
			{
				lastSaveDate: new Date()
			}
		)
		.dispatchAction(
			UPDATE_SAVING_CHANGES_STATUS,
			{
				savingChanges: false
			}
		)
		.dispatchAction(CLEAR_HOVERED_ITEM)
		.dispatchAction(CLEAR_ACTIVE_ITEM);
}

/**
 * Recursively inserts a value inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * @param {!Array|!Object} object Original object that will be copied
 * @param {!Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {*} value Value to be inserted
 * @return {Array|Object} Copy of the original object with the new value
 * @review
 */
function setIn(object, keyPath, value) {
	return updateIn(
		object,
		keyPath,
		() => value
	);
}

/**
 * Returns true if current active element should be clear
 * @param {HTMLElement} oldActiveElement
 * @return {boolean}
 */
function shouldClearFocus(oldActiveElement) {
	const fragmentEntryLinkList = (
		document.querySelector('#wrapper') ||
		document.body
	);
	const newActiveElement = document.activeElement;

	return (
		oldActiveElement &&
		newActiveElement &&
		(oldActiveElement !== newActiveElement) &&
		!contains(oldActiveElement, newActiveElement) &&
		(
			contains(fragmentEntryLinkList, newActiveElement) ||
			(newActiveElement === document.body)
		)
	);
}

/**
 * Recursively inserts the value returned from updater inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 * Updater receives the previous value or defaultValue and returns a new value.
 * @param {!Array|Object} object Original object that will be copied
 * @param {!Array<string>} keyPath Array of strings used for reaching the deep property
 * @param {!Function} updater
 * @param {*} defaultValue
 * @return {Object}
 * @review
 */
function updateIn(object, keyPath, updater, defaultValue) {
	const [nextKey] = keyPath;
	let target = object;

	if (keyPath.length > 1) {
		target = target instanceof Array ?
			[...target] :
			Object.assign({}, target);

		target[nextKey] = updateIn(
			target[nextKey] || {},
			keyPath.slice(1),
			updater,
			defaultValue
		);
	}
	else {
		const nextValue = typeof target[nextKey] === 'undefined' ?
			defaultValue :
			target[nextKey];

		const updatedNextValue = updater(nextValue);

		if (updatedNextValue !== target[nextKey]) {
			target = target instanceof Array ?
				[...target] :
				Object.assign({}, target);

			target[nextKey] = updatedNextValue;
		}
	}

	return target;
}

/**
 * Update layoutData on backend
 * @param {!string} updateLayoutPageTemplateDataURL
 * @param {!string} portletNamespace
 * @param {!string} classNameId
 * @param {!string} classPK
 * @param {!Object} data
 * @param {!Array} fragmentEntryLinkIds
 * @return {Promise}
 * @review
 */
function updateLayoutData(
	updateLayoutPageTemplateDataURL,
	portletNamespace,
	classNameId,
	classPK,
	data,
	fragmentEntryLinkIds
) {
	const formData = new FormData();

	formData.append(`${portletNamespace}classNameId`, classNameId);
	formData.append(`${portletNamespace}classPK`, classPK);

	formData.append(
		`${portletNamespace}data`,
		JSON.stringify(data)
	);

	if (fragmentEntryLinkIds) {
		formData.append(
			`${portletNamespace}fragmentEntryLinkIds`,
			JSON.stringify(fragmentEntryLinkIds)
		);
	}

	return fetch(
		updateLayoutPageTemplateDataURL,
		{
			body: formData,
			credentials: 'include',
			method: 'POST'
		}
	);
}

/**
 * @param {Object} state
 * @param {Object[]} state.fragmentEntryLinks
 * @param {Object[]} state.widgets
 * @param {string} fragmentEntryLinkId
 * @return {Object} Next state
 */
function updateWidgets(state, fragmentEntryLinkId) {
	const fragmentEntryLink = state.fragmentEntryLinks[fragmentEntryLinkId];
	let nextState = state;

	if (fragmentEntryLink.portletId) {
		const widget = getWidget(state.widgets, fragmentEntryLink.portletId);

		if (!widget.instanceable && widget.used) {
			const widgetPath = getWidgetPath(state.widgets, fragmentEntryLink.portletId);

			nextState = setIn(
				state,
				[
					...widgetPath,
					'used'
				],
				false
			);
		}
	}

	return nextState;
}

export {
	add,
	focusItem,
	moveItem,
	remove,
	removeItem,
	setIn,
	shouldClearFocus,
	updateIn,
	updateLayoutData,
	updateWidgets
};