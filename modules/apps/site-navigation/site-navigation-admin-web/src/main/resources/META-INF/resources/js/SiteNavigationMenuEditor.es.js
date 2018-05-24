import {dom} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import State, {Config} from 'metal-state';

import SiteNavigationMenu from './SiteNavigationMenu.es';

import {
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_DRAG_ICON_CLASSNAME,
	SiteNavigationMenuItem
} from './SiteNavigationMenuItem.es';

const KEYS = {
	ARROW_DOWN: 'ArrowDown',
	ARROW_LEFT: 'ArrowLeft',
	ARROW_RIGHT: 'ArrowRight',
	ARROW_UP: 'ArrowUp',
	ENTER: 'Enter',
	SPACEBAR: ' '
};

/**
 *	Site navigation menu editor component.
 */

class SiteNavigationMenuEditor extends State {

	/**
	 * @inheritDoc
	 */

	constructor(config, ...args) {
		super(config, ...args);

		this.setState(config);

		this._dragDrop = new DragDrop(
			{
				dragPlaceholder: Drag.Placeholder.CLONE,
				handles: `.${MENU_ITEM_DRAG_ICON_CLASSNAME}`,
				sources: `.${MENU_ITEM_CLASSNAME}`,
				targets: `.${MENU_ITEM_CLASSNAME}`
			}
		);

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDragItem.bind(this)
		);

		this._dragDrop.on(
			Drag.Events.START,
			this._handleDragStart.bind(this)
		);

		this._dragDrop.on(
			DragDrop.Events.END,
			this._handleDropItem.bind(this)
		);

		dom.on(
			`.${MENU_ITEM_CLASSNAME}`,
			'click',
			this._handleItemClick.bind(this)
		);

		dom.on(
			`.${MENU_ITEM_CLASSNAME}`,
			'keyup',
			this._handleItemKeyUp.bind(this)
		);
	}

	/**
	 * @inheritDoc
	 */

	dispose(...args) {
		this._dragDrop.dispose();

		super.dispose(...args);
	}

	/**
	 * This is called when user drags the item across the container.
	 *
	 * @param {!object} data Drag event data
	 * @param {!Event} event Drag event
	 * @private
	 */

	_handleDragItem(data, event) {
		const placeholderMenuItem = data.placeholder;
		const sourceMenuItem = data.source;

		const nearestMenuItem = SiteNavigationMenu.getNearestMenuItem(
			sourceMenuItem,
			placeholderMenuItem
		);

		if (
			placeholderMenuItem && SiteNavigationMenuItem.isMenuItem(placeholderMenuItem) &&
			sourceMenuItem && SiteNavigationMenuItem.isMenuItem(sourceMenuItem) &&
			nearestMenuItem && SiteNavigationMenuItem.isMenuItem(nearestMenuItem)
		) {
			const nested = SiteNavigationMenu.shouldBeNested(
				placeholderMenuItem,
				nearestMenuItem
			);

			const over = SiteNavigationMenu.isOver(
				placeholderMenuItem,
				nearestMenuItem
			);

			if (!over && nested) {
				SiteNavigationMenu.insertAtPosition(
					nearestMenuItem,
					sourceMenuItem,
					0
				);
			}
			else {
				const nearestMenuItemParent = SiteNavigationMenuItem.getParent(
					nearestMenuItem
				);

				const nearestMenuItemIndex = SiteNavigationMenuItem.getChildren(nearestMenuItemParent).indexOf(nearestMenuItem) + (over ? 0 : 1);

				SiteNavigationMenu.insertAtPosition(
					nearestMenuItemParent,
					sourceMenuItem,
					nearestMenuItemIndex
				);
			}
		}
	}

	/**
	 * This is called when user starts to drag the item across the container.
	 *
	 * @param {!object} data Drag event data
	 * @param {!Event} event Drag event
	 * @private
	 */

	_handleDragStart(data, event) {
		const menuItem = event.target.getActiveDrag();

		SiteNavigationMenuItem.setDragging(menuItem, true);
	}

	/**
	 * This is called when user drops the item on the container.
	 *
	 * @param {!object} data Drop event data
	 * @param {!Event} event Drop event
	 * @private
	 */

	_handleDropItem(data, event) {
		event.preventDefault();

		const menuItem = data.source;
		const menuItemId = SiteNavigationMenuItem.getId(menuItem);

		const menuItemIndex = SiteNavigationMenuItem
			.getSiblings(menuItem)
			.indexOf(menuItem);

		const menuItemParentId = SiteNavigationMenuItem.getId(
			SiteNavigationMenuItem.getParent(menuItem)
		);

		this._updateParentAndOrder(
			{
				dragOrder: menuItemIndex,
				parentId: menuItemParentId,
				siteNavigationMenuItemId: menuItemId
			}
		);

		SiteNavigationMenuItem.setDragging(menuItem, false);
	}

	/**
	 * This is called when user clicks on menu item.
	 *
	 * @param {!Event} event Click event data
	 * @private
	 */

	_handleItemClick(event) {
		event.stopPropagation();

		const menuItem = event.delegateTarget;

		SiteNavigationMenuItem.setSelected(menuItem, true);

		menuItem.focus();
	}

	/**
	 * This is called when user presses a key on menu item.
	 *
	 * @param {!Event} event KeyUp event data
	 * @private
	 */

	_handleItemKeyUp(event) {
		const menuItem = event.delegateTarget;
		const menuItemParent = SiteNavigationMenuItem.getParent(menuItem);
		const menuItemParentId = SiteNavigationMenuItem.getId(menuItemParent);
		const menuItemSelected = SiteNavigationMenuItem.isSelected(menuItem);
		const menuItemSiblings = SiteNavigationMenuItem.getSiblings(menuItem);

		const menuItemIndex = menuItemSiblings.indexOf(menuItem);

		let layoutModified = false;

		if (event.key === KEYS.ENTER || event.key === KEYS.SPACEBAR) {
			menuItem.click();
		}
		else if (
			menuItemSelected &&
			(event.key === KEYS.ARROW_LEFT) &&
			menuItemParentId
		) {
			const grandParentItem = SiteNavigationMenuItem.getParent(menuItemParent);

			grandParentItem.insertBefore(
				menuItem,
				SiteNavigationMenuItem.getNextSibling(menuItemParent)
			);

			const parentItems = this._getMenuItemSiblings(menuItem);
			menuItem.dataset.order = parentItems.indexOf(menuItem).toString();

			layoutModified = true;
		}
		else if (
			menuItemSelected &&
			(event.key === KEYS.ARROW_UP) &&
			(menuItemIndex > 0)
		) {
			const newIndex = menuItemIndex - 1;

			const menuItemSibling = menuItemSiblings[newIndex];

			menuItemParent.insertBefore(
				menuItem,
				menuItemSibling
			);

			menuItem.dataset.order = newIndex;

			layoutModified = true;
		}
		else if (
			menuItemSelected &&
			(event.key === KEYS.ARROW_RIGHT) &&
			(menuItemIndex > 0)
		) {
			const newIndex = menuItemIndex - 1;

			const menuItemSibling = menuItemSiblings[newIndex];

			menuItemSibling.appendChild(menuItem);

			const parentItems = this._getMenuItemSiblings(menuItemSibling);
			menuItem.dataset.order = parentItems.indexOf(menuItem).toString();

			layoutModified = true;
		}
		else if (
			menuItemSelected &&
			(event.key === KEYS.ARROW_DOWN) &&
			(menuItemIndex < menuItemSiblings.length - 1)
		) {
			const newIndex = menuItemIndex + 1;

			if (newIndex < menuItemSiblings.length - 1) {
				const menuItemSibling = menuItemSiblings[newIndex];

				menuItemParent.insertBefore(
					menuItem,
					SiteNavigationMenuItem.getNextSibling(menuItemSibling)
				);
			}
			else {
				menuItemParent.appendChild(menuItem);
			}

			layoutModified = true;
		}

		if (layoutModified) {
			const dragOrder = SiteNavigationMenuItem
				.getSiblings(menuItem)
				.indexOf(menuItem);

			this._updateParentAndOrder(
				{
					dragOrder,
					parentId: menuItemParentId,
					siteNavigationMenuItemId: SiteNavigationMenuItem.getId(menuItem)
				}
			);

			menuItem.click();
		}
	}

	/**
	 * Send layout information to the server and returns a promise
	 * that resolves after finishing.
	 * @param {{
	 *   dragOrder: !string,
	 *   parentId: !string,
	 *   siteNavigationMenuItemId: !string
	 * }} data
	 * @private
	 * @return {Promise}
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

		formData.append(
			`${this.namespace}order`,
			data.dragOrder
		);

		return fetch(
			this.editSiteNavigationMenuItemParentURL,
			{
				body: formData,
				credentials: 'include',
				method: 'POST'
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */

SiteNavigationMenuEditor.STATE = {

	/**
	 * URL for edit site navigation menu item parent action.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */

	editSiteNavigationMenuItemParentURL: Config.string().required(),

	/**
	 * Portlet namespace to use in edit action.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */

	namespace: Config.string().required(),

	/**
	 * Internal DragDrop instance.
	 *
	 * @default null
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {State}
	 */

	_dragDrop: Config.internal().value(null)
};

export {SiteNavigationMenuEditor};
export default SiteNavigationMenuEditor;