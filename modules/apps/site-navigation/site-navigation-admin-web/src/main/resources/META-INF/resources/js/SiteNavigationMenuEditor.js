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

import {fetch} from 'frontend-js-web';
import {dom} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import State, {Config} from 'metal-state';

import {
	getNearestMenuItem,
	insertAtPosition,
	isOver,
	shouldBeNested
} from './SiteNavigationMenuDOMHandler';
import {
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_CONTENT_CLASSNAME,
	MENU_ITEM_DRAG_ICON_CLASSNAME,
	getChildren,
	getFromContentElement,
	getFromId,
	getId,
	getParent,
	getSiblings,
	isMenuItem,
	setDragging,
	setSelected,
	unselectAll
} from './SiteNavigationMenuItemDOMHandler';

/**
 * List of keys used for moving elements with the keyboard.
 */
const KEYS = {
	ARROW_DOWN: 'ArrowDown',
	ARROW_LEFT: 'ArrowLeft',
	ARROW_RIGHT: 'ArrowRight',
	ARROW_UP: 'ArrowUp',
	ENTER: 'Enter',
	SPACEBAR: ' '
};

/**
 * Provides the Site Navigation Menu Editor.
 */
class SiteNavigationMenuEditor extends State {
	/**
	 * @inheritDoc
	 */
	constructor(config, ...args) {
		super(config, ...args);

		const controlMenu = document.querySelector('.control-menu');
		this._controlMenuHeight = controlMenu ? controlMenu.offsetHeight : 0;

		const managementBar = document.querySelector('.management-bar');
		this._managementBarHeight = managementBar
			? managementBar.offsetHeight
			: 0;

		this.setState(config);

		this._dragDrop = new DragDrop({
			autoScroll: true,
			dragPlaceholder: Drag.Placeholder.CLONE,
			handles: `.${MENU_ITEM_DRAG_ICON_CLASSNAME}`,
			sources: `.${MENU_ITEM_CLASSNAME}`,
			targets: `.${MENU_ITEM_CLASSNAME}`
		});

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDragItem.bind(this)
		);

		this._dragDrop.on(Drag.Events.START, this._handleDragStart.bind(this));

		this._dragDrop.on(DragDrop.Events.END, this._handleDropItem.bind(this));

		this._itemClickHandler = dom.on(
			`.${MENU_ITEM_CONTENT_CLASSNAME}`,
			'click',
			this._handleItemClick.bind(this)
		);

		this._itemKeyUpHandler = dom.on(
			`.${MENU_ITEM_CLASSNAME}`,
			'keyup',
			this._handleItemKeyUp.bind(this)
		);

		this.on(
			'selectedMenuItemChanged',
			this._handleSelectedMenuItemChanged.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 */
	dispose(...args) {
		if (this._dragDrop) {
			this._dragDrop.dispose();
			this._dragDrop = null;
		}

		if (this._itemClickHandler) {
			this._itemClickHandler.removeListener();
			this._itemClickHandler = null;
		}

		if (this._itemKeyUpHandler) {
			this._itemKeyUpHandler.removeListener();
			this._itemKeyUpHandler = null;
		}

		super.dispose(...args);
	}

	/**
	 * Handles the event when the user drags the item across the container.
	 *
	 * @param {!object} data The drag event data.
	 * @private
	 */
	_handleDragItem(data) {
		const placeholderMenuItem = data.placeholder;
		const sourceMenuItem = data.source;

		const nearestMenuItem = getNearestMenuItem(
			sourceMenuItem,
			placeholderMenuItem
		);

		if (!this._draggedItemRegion) {
			this._draggedItemRegion = position.getRegion(placeholderMenuItem);
		}

		this._draggedItemRegion = position.getRegion(placeholderMenuItem);

		if (
			placeholderMenuItem &&
			isMenuItem(placeholderMenuItem) &&
			sourceMenuItem &&
			isMenuItem(sourceMenuItem) &&
			nearestMenuItem &&
			isMenuItem(nearestMenuItem)
		) {
			const nested = shouldBeNested(placeholderMenuItem, nearestMenuItem);

			const over = isOver(placeholderMenuItem, nearestMenuItem);

			if (!over && nested) {
				insertAtPosition(nearestMenuItem, sourceMenuItem, 0);
			}
			else {
				const nearestMenuItemParent = getParent(nearestMenuItem);

				const nearestMenuItemIndex =
					getChildren(nearestMenuItemParent).indexOf(
						nearestMenuItem
					) + (over ? 0 : 1);

				insertAtPosition(
					nearestMenuItemParent,
					sourceMenuItem,
					nearestMenuItemIndex
				);
			}
		}
	}

	/**
	 * Handles the event when the user starts to drag the item across the
	 * container.
	 *
	 * @param {!object} data The drag event data.
	 * @param {!Event} event The drag event.
	 * @private
	 */
	_handleDragStart(data, event) {
		const menuItem = event.target.getActiveDrag();

		this.selectedMenuItem = menuItem;

		setDragging(menuItem, true);
	}

	/**
	 * Handles the event when the user drops the item on the container.
	 *
	 * @param {!object} data The drop event data.
	 * @param {!Event} event The drop event.
	 * @private
	 */
	_handleDropItem(data, event) {
		event.preventDefault();

		const menuItem = data.source;
		const menuItemId = getId(menuItem);

		const menuItemIndex = getSiblings(menuItem).indexOf(menuItem);

		const menuItemParentId = getId(getParent(menuItem));

		this._draggedItemRegion = null;

		this._updateParentAndOrder({
			dragOrder: menuItemIndex,
			parentId: menuItemParentId,
			siteNavigationMenuItemId: menuItemId
		});

		setDragging(menuItem, false);
	}

	/**
	 * Handles the event when the user clicks on a menu item.
	 *
	 * @param {!Event} event The click event data.
	 * @private
	 */
	_handleItemClick(event) {
		const menuItem = getFromContentElement(event.delegateTarget);

		this.selectedMenuItem = menuItem;
	}

	/**
	 * Handles the event when the user presses a key on a menu item.
	 *
	 * @param {!Event} event The key up event data.
	 * @private
	 */
	_handleItemKeyUp(event) {
		event.stopPropagation();

		const menuItem = event.delegateTarget;
		const menuItemIndex = getSiblings(menuItem).indexOf(menuItem);

		const menuItemParent = getParent(menuItem);

		let layoutModified = false;

		if (event.key === KEYS.ENTER || event.key === KEYS.SPACEBAR) {
			this.selectedMenuItem = menuItem;
		}
		else if (event.key === KEYS.ARROW_LEFT) {
			const menuItemParentIndex = getSiblings(menuItemParent).indexOf(
				menuItemParent
			);

			const menuItemGrandParent = getParent(menuItemParent);

			if (menuItemParentIndex !== -1) {
				insertAtPosition(
					menuItemGrandParent,
					menuItem,
					menuItemParentIndex + 1
				);
			}

			layoutModified = true;
		}
		else if (event.key === KEYS.ARROW_UP && menuItemIndex > 0) {
			insertAtPosition(menuItemParent, menuItem, menuItemIndex - 1);

			layoutModified = true;
		}
		else if (event.key === KEYS.ARROW_RIGHT && menuItemIndex > 0) {
			const previousSibling = getSiblings(menuItem)[menuItemIndex - 1];

			insertAtPosition(previousSibling, menuItem, Infinity);

			layoutModified = true;
		}
		else if (event.key === KEYS.ARROW_DOWN) {
			insertAtPosition(menuItemParent, menuItem, menuItemIndex + 2);

			layoutModified = true;
		}

		if (layoutModified) {
			const siteNavigationMenuItemId = getId(menuItem);

			this._updateParentAndOrder({
				dragOrder: getSiblings(menuItem).indexOf(menuItem),

				parentId: getId(getParent(menuItem)),

				siteNavigationMenuItemId
			});

			requestAnimationFrame(() => {
				const modifiedMenuItem = getFromId(siteNavigationMenuItemId);

				modifiedMenuItem.focus();
			});
		}
	}

	/**
	 * Handles the event when the selected menu item property changes.
	 *
	 * @param {{newVal: HTMLElement|null}} event
	 * @private
	 */
	_handleSelectedMenuItemChanged(event) {
		unselectAll();

		if (event.newVal) {
			setSelected(event.newVal);
		}
	}

	/**
	 * Sends layout information to the server and returns a promise that
	 * resolves after finishing.
	 *
	 * @param {{
	 *   dragOrder: !string,
	 *   parentId: !string,
	 *   siteNavigationMenuItemId: !string
	 * }} data
	 * @private
	 * @return {Promise} The promise.
	 */
	_updateParentAndOrder(data) {
		const formData = new FormData();

		formData.append(
			`${this.namespace}siteNavigationMenuItemId`,
			data.siteNavigationMenuItemId
		);

		formData.append(
			`${this.namespace}parentSiteNavigationMenuItemId`,
			data.parentId
		);

		formData.append(`${this.namespace}order`, data.dragOrder);

		return fetch(this.editSiteNavigationMenuItemParentURL, {
			body: formData,
			method: 'POST'
		});
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
SiteNavigationMenuEditor.STATE = {
	/**
	 * Control menu height.
	 *
	 * @default 0
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @type {number}
	 */
	_controlMenuHeight: Config.number()
		.internal()
		.value(0),

	/**
	 * @default -1
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @type {number}
	 */
	_currentYPosition: Config.number()
		.internal()
		.value(-1),

	/**
	 * Internal <code>DragDrop</code> instance.
	 *
	 * @default null
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {object|null}
	 */
	_dragDrop: Config.internal().value(null),

	/**
	 * Dragged item.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @type {HTMLElement|null}
	 */
	_draggedItemRegion: Config.object().internal(),

	/**
	 * Management bar height.
	 *
	 * @default 0
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @type {number}
	 */
	_managementBarHeight: Config.number()
		.internal()
		.value(0),

	/**
	 * URL for the edit site navigation menu item parent action.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */
	editSiteNavigationMenuItemParentURL: Config.string().required(),

	/**
	 * Portlet namespace to use in the edit action.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */
	namespace: Config.string().required(),

	/**
	 * Selected menu item DOM element.
	 *
	 * @default null
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {HTMLElement}
	 */
	selectedMenuItem: Config.object().value(null)
};

export {SiteNavigationMenuEditor};
export default SiteNavigationMenuEditor;
