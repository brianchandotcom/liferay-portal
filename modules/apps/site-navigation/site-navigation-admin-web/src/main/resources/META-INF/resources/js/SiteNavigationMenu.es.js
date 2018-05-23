import SiteNavigationMenuItem from './SiteNavigationMenuItem.es';

/**
 * Set of static functions for managing site navigation menu
 * querying and mutation.
 * @type {Object}
 */

const SiteNavigationMenu = {

	/**
	 * Inserts the given menuItem as child of given parentMenuItem
	 * at the given position.
	 * @param {HTMLElement} parentMenuItem
	 * @param {HTMLElement} menuItem
	 * @param {number} position
	 * @review
	 */

	insertAtPosition: function(parentMenuItem, menuItem, position) {
		const childrenMenuItems = SiteNavigationMenuItem.getChildren(parentMenuItem);

		if (position >= childrenMenuItems.length) {
			parentMenuItem.appendChild(menuItem);
		}
		else {
			const nextMenuItem = childrenMenuItems[position];

			parentMenuItem.insertBefore(
				menuItem,
				nextMenuItem
			);
		}
	}
};

export {SiteNavigationMenu};
export default SiteNavigationMenu;