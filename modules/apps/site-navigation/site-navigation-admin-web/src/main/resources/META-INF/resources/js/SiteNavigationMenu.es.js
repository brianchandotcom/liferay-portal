import {contains, hasClass, toElement} from 'metal-dom';
import position from 'metal-position';

import {
	MENU_CONTAINER_CLASSNAME,
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_DRAGGING_CLASSNAME,
	SiteNavigationMenuItem
} from './SiteNavigationMenuItem.es';

/**
 * Set of static functions for managing site navigation menu
 * querying and mutation.
 * @type {Object}
 */

const SiteNavigationMenu = {

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

	getNearestMenuItem: function(originMenuItem, placeholder) {
		const container = toElement(`.${MENU_CONTAINER_CLASSNAME}`);
		const originMenuItemId = SiteNavigationMenuItem.getId(originMenuItem);
		const originMenuItemRegion = position.getRegion(placeholder);

		return Array.prototype
			.slice.call(
				document.querySelectorAll(`.${MENU_ITEM_CLASSNAME}`)
			)
			.filter(
				menuItem => (
					(contains(container, menuItem)) &&
					(!contains(originMenuItem, menuItem)) &&
					(SiteNavigationMenuItem.getId(menuItem) !== originMenuItemId) &&
					(!hasClass(menuItem, MENU_ITEM_DRAGGING_CLASSNAME))
				)
			)
			.map(
				menuItem => {
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
				}
			)
			.reduce(
				(distanceA, distanceB) => {
					return distanceA.distance < distanceB.distance ?
						distanceA : distanceB;
				},
				{
					distance: Infinity,
					menuItem: null
				}
			)
			.menuItem;
	},

	/**
	 * Inserts the given menuItem as child of given parentMenuItem
	 * at the given position.
	 * @param {HTMLElement} parentMenuItem
	 * @param {HTMLElement} menuItem
	 * @param {number} position
	 * @review
	 */

	insertAtPosition: function(parentMenuItem, menuItem, position) {
		const children = SiteNavigationMenuItem.getChildren(parentMenuItem);

		if (position >= children.length) {
			parentMenuItem.appendChild(menuItem);
		}
		else {
			parentMenuItem.insertBefore(
				menuItem,
				children[position]
			);
		}
	},

	/**
	 * Insert the given menuItem at the top of the navigation tree
	 * @param {HTMLElement} menuItem
	 */

	insertAtTop: function(menuItem) {
		const container = toElement(`.${MENU_CONTAINER_CLASSNAME}`);

		const children = SiteNavigationMenuItem.getChildren(container);

		if (children.length) {
			container.insertBefore(
				menuItem,
				children[0]
			);
		}
		else {
			container.appendChild(menuItem);
		}
	},

	/**
	 * Returns true if the first menuItem element is over
	 * the second menuItem element.
	 * @param {HTMLElement} menuItemA
	 * @param {HTMLElement} menuItemB
	 * @return {boolean}
	 */

	isOver: function(menuItemA, menuItemB) {
		const menuItemARegion = position.getRegion(menuItemA);
		const menuItemBRegion = position.getRegion(menuItemB);

		return menuItemARegion.top < menuItemBRegion.top;
	},

	/**
	 * Returns true if the given menuItem element should be nested
	 * inside the given parentMenuItem element by checking their positions.
	 * @param {HTMLElement} menuItem
	 * @param {HTMLElement} parentMenuItem
	 * @return {boolean}
	 */

	shouldBeNested: function(menuItem, parentMenuItem) {
		let nested = false;

		if (SiteNavigationMenuItem.isMenuItem(parentMenuItem)) {
			const menuItemRegion = position.getRegion(menuItem);
			const parentMenuItemRegion = position.getRegion(parentMenuItem);

			const nestedInParent = menuItemRegion.left >
				(parentMenuItemRegion.left + 100);

			const parentWithChildren = SiteNavigationMenuItem
				.getChildren(parentMenuItem)
				.filter(
					childMenuItem => SiteNavigationMenuItem.getId(childMenuItem) !==
						SiteNavigationMenuItem.getId(menuItem)
				)
				.length > 0;

			nested = nestedInParent || parentWithChildren;
		}

		return nested;
	}
};

export {SiteNavigationMenu};
export default SiteNavigationMenu;