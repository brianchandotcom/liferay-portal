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

import {
	addClasses,
	closest,
	contains,
	hasClass,
	next,
	removeClasses,
	toElement
} from 'metal-dom';

const MENU_CONTAINER_CLASSNAME = 'site-navigation-menu-container';

const MENU_ITEM_CLASSNAME = 'site-navigation-menu-item';

const MENU_ITEM_CONTENT_CLASSNAME = `${MENU_ITEM_CLASSNAME}__content`;

const MENU_ITEM_DRAGGING_CLASSNAME = `${MENU_ITEM_CLASSNAME}--dragging`;

const MENU_ITEM_DRAG_ICON_CLASSNAME = `${MENU_ITEM_CLASSNAME}__drag-icon`;

const MENU_ITEM_SELECTED_CLASSNAME = `${MENU_ITEM_CLASSNAME}--selected`;

/**
 * Returns the menu item element's children.
 *
 * @param {HTMLElement} menuItem The menu item to return children for.
 * @return {Array<HTMLElement>}
 */
const getChildren = function(menuItem) {
	return Array.prototype.slice
		.call(menuItem.children)
		.filter(child => hasClass(child, MENU_ITEM_CLASSNAME));
};

/**
 * Returns the parent menu item element of the menu item content.
 *
 * @param {HTMLElement} menuItemContent The menu item content to return the
 * parent menu item for.
 * @return {HTMLElement|null}
 */
const getFromContentElement = function(menuItemContent) {
	return closest(menuItemContent, `.${MENU_ITEM_CLASSNAME}`);
};

/**
 * Returns the menu item element with the given ID.
 *
 * @param {number|string} menuItemId The menu item's ID.
 * @return {HTMLElement|null}
 */
const getFromId = function(menuItemId) {
	return document.querySelector(
		`.${MENU_ITEM_CLASSNAME}[data-site-navigation-menu-item-id="${menuItemId}"]`
	);
};

/**
 * Returns the given menu item element's ID.
 *
 * @param {HTMLElement} menuItem The menu item
 * @return {number}
 */
const getId = function(menuItem) {
	return parseInt(menuItem.dataset.siteNavigationMenuItemId, 10) || 0;
};

/**
 * Returns the next menu item sibling of the given menu item element.
 *
 * @param {HTMLElement} menuItem The menu item.
 * @return {HTMLElement|null}
 */
const getNextSibling = function(menuItem) {
	next(menuItem, `.${MENU_ITEM_CLASSNAME}`);
};

/**
 * Returns the menu item element's parent.
 *
 * @param {HTMLElement} menuItem The menu item.
 * @return {HTMLElement|null}
 */
const getParent = function(menuItem) {
	return menuItem.parentElement;
};

/**
 * Returns the menu item element's siblings.
 *
 * @param {HTMLElement} menuItem The menu item.
 * @return {Array<HTMLElement>}
 */
const getSiblings = function(menuItem) {
	const parentElement = menuItem.parentElement;
	let siblings = [];

	if (parentElement) {
		siblings = getChildren(parentElement);
	}

	return siblings;
};

/**
 * Returns <code>true</code> if the menu item is a child of the parent menu
 * item.
 *
 * @param {HTMLElement} menuItem The menu item to check.
 * @param {HTMLElement} parentMenuItem The parent menu item.
 * @return {boolean} Whether the menu item is a child of the parent menu item.
 */
const isChildOf = function(menuItem, parentMenuItem) {
	return contains(parentMenuItem, menuItem);
};

/**
 * Returns <code>true</code> if the given HTML element is a menu item.
 * @param {HTMLElement} htmlElement The HTML element to check.
 * @return {boolean} Whether the HTML element is a menu item.
 */
const isMenuItem = function(htmlElement) {
	return hasClass(htmlElement, MENU_ITEM_CLASSNAME);
};

/**
 * Returns <code>true</code> if the given menu item element is selected.
 *
 * @param {HTMLElement} menuItem The menu item to check.
 * @return {boolean} Whether the menu item is selected.
 */
const isSelected = function(menuItem) {
	return hasClass(menuItem, MENU_ITEM_SELECTED_CLASSNAME);
};

/**
 * Mutates the given menu item element by changing it's status to
 * dragging or not dragging.
 * @param {HTMLElement} menuItem The menu item.
 */
const setDragging = function(menuItem, dragging = false) {
	if (dragging) {
		addClasses(menuItem, MENU_ITEM_DRAGGING_CLASSNAME);
	}
	else {
		removeClasses(menuItem, MENU_ITEM_DRAGGING_CLASSNAME);
	}
};

/**
 * Mutates the given menu item by changing it's status to selected. Only a
 * single menu item can be selected, so any other selected menu item will be
 * unselected.
 *
 * @param {!HTMLElement} menuItem The menu item.
 */
const setSelected = function(menuItem) {
	unselectAll();
	addClasses(menuItem, MENU_ITEM_SELECTED_CLASSNAME);
};

/**
 * Mutates all selected menu items and sets their status to unselected.
 */
const unselectAll = function() {
	const selectedMenuItem = toElement(`.${MENU_ITEM_SELECTED_CLASSNAME}`);

	if (selectedMenuItem) {
		removeClasses(selectedMenuItem, MENU_ITEM_SELECTED_CLASSNAME);
	}
};

export {
	MENU_CONTAINER_CLASSNAME,
	MENU_ITEM_CONTENT_CLASSNAME,
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_DRAG_ICON_CLASSNAME,
	MENU_ITEM_DRAGGING_CLASSNAME,
	getChildren,
	getFromContentElement,
	getFromId,
	getId,
	getNextSibling,
	getParent,
	getSiblings,
	isChildOf,
	isMenuItem,
	isSelected,
	setDragging,
	setSelected,
	unselectAll
};
