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

import {contains, hasClass, toElement} from 'metal-dom';
import position from 'metal-position';

import {
	getChildren,
	getId,
	isMenuItem,
	MENU_CONTAINER_CLASSNAME,
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_DRAGGING_CLASSNAME
} from './SiteNavigationMenuItemDOMHandler.es';

/**
 * Constant indicating how much a menuItem element should be moved
 * to the right in order to consider it nested to it's parent.
 */

const NEST_THRESHOLD = 30;

/**
 * Gets the nearest menuItem element for the given originMenuItem
 * and placeholder.
 *
 * The placeholder is used for retrieving the reference position, and
 * the originMenuItem for checking that child elements are being avoided.
 *
 * @param {HTMLElement} originMenuItem
 * @param {HTMLElement} placeholder
 * @return {HTMLElement}
 */

const getNearestMenuItem = function(originMenuItem, placeholder) {
	const container = toElement(`.${MENU_CONTAINER_CLASSNAME}`);
	const originMenuItemId = getId(originMenuItem);
	const originMenuItemRegion = position.getRegion(placeholder);

	return Array.prototype.slice
		.call(document.querySelectorAll(`.${MENU_ITEM_CLASSNAME}`))
		.filter(
			menuItem =>
				contains(container, menuItem) &&
				!contains(originMenuItem, menuItem) &&
				getId(menuItem) !== originMenuItemId &&
				!hasClass(menuItem, MENU_ITEM_DRAGGING_CLASSNAME)
		)
		.map(menuItem => {
			const menuItemRegion = position.getRegion(menuItem);

			const distance = Math.sqrt(
				Math.pow(originMenuItemRegion.left - menuItemRegion.left, 2) +
					Math.pow(originMenuItemRegion.top - menuItemRegion.top, 2)
			);

			return {
				distance,
				menuItem,
				region: menuItemRegion
			};
		})
		.reduce(
			(distanceA, distanceB) => {
				return distanceA.distance < distanceB.distance
					? distanceA
					: distanceB;
			},
			{
				distance: Infinity,
				menuItem: null
			}
		).menuItem;
};

/**
 * Inserts the given menuItem as child of given parentMenuItem
 * at the given position.
 * @param {HTMLElement} parentMenuItem
 * @param {HTMLElement} menuItem
 * @param {number} position
 * @review
 */

const insertAtPosition = function(parentMenuItem, menuItem, position) {
	const children = getChildren(parentMenuItem);

	if (position >= children.length) {
		parentMenuItem.appendChild(menuItem);
	} else {
		parentMenuItem.insertBefore(menuItem, children[position]);
	}
};

/**
 * Insert the given menuItem at the top of the navigation tree
 * @param {HTMLElement} menuItem
 */

const insertAtTop = function(menuItem) {
	const container = toElement(`.${MENU_CONTAINER_CLASSNAME}`);

	const children = getChildren(container);

	if (children.length) {
		container.insertBefore(menuItem, children[0]);
	} else {
		container.appendChild(menuItem);
	}
};

/**
 * Returns true if the first menuItem element is over
 * the second menuItem element.
 * @param {HTMLElement} menuItemA
 * @param {HTMLElement} menuItemB
 * @return {boolean}
 */

const isOver = function(menuItemA, menuItemB) {
	const menuItemARegion = position.getRegion(menuItemA);
	const menuItemBRegion = position.getRegion(menuItemB);

	return menuItemARegion.top < menuItemBRegion.top;
};

/**
 * Returns true if the given menuItem element should be nested
 * inside the given parentMenuItem element by checking their positions.
 * @param {HTMLElement} menuItem
 * @param {HTMLElement} parentMenuItem
 * @return {boolean}
 */

const shouldBeNested = (menuItem, parentMenuItem) => {
	let nested = false;

	if (isMenuItem(parentMenuItem)) {
		const menuItemRegion = position.getRegion(menuItem);
		const parentMenuItemRegion = position.getRegion(parentMenuItem);

		const nestedInParent =
			menuItemRegion.left > parentMenuItemRegion.left + NEST_THRESHOLD;

		const parentWithChildren =
			getChildren(parentMenuItem).filter(
				childMenuItem => getId(childMenuItem) !== getId(menuItem)
			).length > 0;

		nested = nestedInParent || parentWithChildren;
	}

	return nested;
};

export {
	getNearestMenuItem,
	insertAtPosition,
	insertAtTop,
	isOver,
	shouldBeNested
};
