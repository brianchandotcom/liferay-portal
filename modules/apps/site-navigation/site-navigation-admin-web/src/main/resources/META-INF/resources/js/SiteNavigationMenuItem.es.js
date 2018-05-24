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

const MENU_ITEM_DRAGGING_CLASSNAME = `${MENU_ITEM_CLASSNAME}--dragging`;

const MENU_ITEM_DRAG_ICON_CLASSNAME = `${MENU_ITEM_CLASSNAME}__drag-icon`;

const MENU_ITEM_SELECTED_CLASSNAME = `${MENU_ITEM_CLASSNAME}--selected`;

/**
 * Set of static functions for managing site navigation menu item
 * DOM querying and mutation.
 * @type {Object}
 */

const SiteNavigationMenuItem = {

	/**
	 * Returns an array with the menuItem children of the given menuItem.
	 * @param {HTMLElement} menuItem
	 * @return {Array<HTMLElement>}
	 * @review
	 */

	getChildren: function(menuItem) {
		return Array.prototype
			.slice.call(menuItem.children)
			.filter(child => hasClass(child, MENU_ITEM_CLASSNAME));
	},

	/**
	 * Returns a menuItem element, parent of a given menuItemContent.
	 * @param {HTMLElement} menuItemContent
	 * @return {HTMLElement|null}
	 * @review
	 */

	getFromContent: function(menuItemContent) {
		return closest(menuItemContent, `.${MENU_ITEM_CLASSNAME}`);
	},

	/**
	 * Returns a menuItem element with the given ID
	 * @param {number|string} menuItemId
	 * @return {HTMLElement|null}
	 */

	getFromId: function(menuItemId) {
		return document.querySelector(
			`.${MENU_ITEM_CLASSNAME}[data-site-navigation-menu-item-id]=${menuItemId}`
		);
	},

	/**
	 * Gets the ID of a given menuItem element.
	 * @param {HTMLElement} menuItem
	 * @return {number}
	 */

	getId: function(menuItem) {
		return parseInt(menuItem.dataset.siteNavigationMenuItemId, 10) || 0;
	},

	/**
	 * Returns the next menuItem sibling of a given menuItem element.
	 * @param {HTMLElement} menuItem
	 * @return {HTMLElement|null}
	 */

	getNextSibling(menuItem) {
		next(menuItem, `.${MENU_ITEM_CLASSNAME}`);
	},

	/**
	 * Returns a menuItem element, parent of a given menuItem.
	 * @param {HTMLElement} menuItem
	 * @return {HTMLElement|null}
	 * @review
	 */

	getParent: function(menuItem) {
		return menuItem.parentElement;
	},

	/**
	 * For a given menuItem element, returns it's sibblings
	 * @param {HTMLElement} menuItem
	 * @return {Array<HTMLElement>}
	 * @review
	 */

	getSiblings: function(menuItem) {
		const parentElement = menuItem.parentElement;
		let siblings = [];

		if (parentElement) {
			siblings = SiteNavigationMenuItem.getChildren(
				parentElement
			);
		}

		return siblings;
	},

	/**
	 * Returns true if the given menuItem is child of the given parentMenuItem
	 * @param {HTMLElement} menuItem
	 * @param {HTMLElement} parentMenuItem
	 * @return {boolean}
	 */

	isChildOf: function(menuItem, parentMenuItem) {
		return contains(parentMenuItem, menuItem);
	},

	/**
	 * Returns true if the given htmlElement is a menuItem, false otherwise.
	 * @param {HTMLElement} htmlElement
	 * @return {boolean}
	 */

	isMenuItem: function(htmlElement) {
		return hasClass(htmlElement, MENU_ITEM_CLASSNAME);
	},

	/**
	 * Returns true if the given menuItem element is selected, false otherwise
	 * @param {HTMLElement} menuItem
	 * @return {boolean}
	 */

	isSelected: function(menuItem) {
		return hasClass(menuItem, MENU_ITEM_SELECTED_CLASSNAME);
	},

	/**
	 * Mutates the given menuItem element by changing it's status
	 * to dragging/not dragging.
	 * @param {HTMLElement} menuItem
	 */

	setDragging: function(menuItem, dragging = false) {
		if (dragging) {
			addClasses(menuItem, MENU_ITEM_DRAGGING_CLASSNAME);
		}
		else {
			removeClasses(menuItem, MENU_ITEM_DRAGGING_CLASSNAME);
		}
	},

	/**
	 * Mutates the given menuItem by changing it's status
	 * to selected/unselected. Only a single menuItem can be
	 * selected, so if selected is changed to true,
	 * any other selected menuItem will be unselected.
	 * @param {HTMLElement} menuItem
	 * @param {boolean} [selected=false]
	 */

	setSelected: function(menuItem, selected = false) {
		if (selected) {
			const selectedMenuItem = toElement(`.${MENU_ITEM_SELECTED_CLASSNAME}`);

			if (selectedMenuItem) {
				SiteNavigationMenuItem.setSelected(selectedMenuItem, false);
			}

			addClasses(menuItem, MENU_ITEM_SELECTED_CLASSNAME);
		}
		else {
			removeClasses(menuItem, MENU_ITEM_SELECTED_CLASSNAME);
		}
	}
};

export {
	MENU_CONTAINER_CLASSNAME,
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_DRAG_ICON_CLASSNAME,
	MENU_ITEM_DRAGGING_CLASSNAME,
	SiteNavigationMenuItem
};

export default SiteNavigationMenuItem;