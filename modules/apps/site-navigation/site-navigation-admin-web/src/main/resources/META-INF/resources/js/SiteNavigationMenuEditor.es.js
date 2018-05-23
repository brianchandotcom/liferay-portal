import State, {Config} from 'metal-state';
import {addClasses, closest, dom, hasClass, match, next, removeClasses} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';

const KEYS = {
	ARROW_DOWN: 'ArrowDown',
	ARROW_LEFT: 'ArrowLeft',
	ARROW_RIGHT: 'ArrowRight',
	ARROW_UP: 'ArrowUp',
	ENTER: 'Enter',
	SPACEBAR: ' '
};

const MENU_CONTAINER_CLASSNAME = 'site-navigation-menu-container';

const MENU_ITEM_CLASSNAME = 'site-navigation-menu-item';

const MENU_ITEM_CONTENT_CLASSNAME = `${MENU_ITEM_CLASSNAME}__content`;

const MENU_ITEM_DRAGGING_CLASSNAME = `${MENU_ITEM_CLASSNAME}--dragging`;

const MENU_ITEM_DRAG_ICON_CLASSNAME = `${MENU_ITEM_CLASSNAME}__drag-icon`;

const MENU_ITEM_NESTED_CLASSNAME = `${MENU_ITEM_CLASSNAME}--nested`;

const MENU_ITEM_SELECTED_CLASSNAME = `${MENU_ITEM_CLASSNAME}--selected`;

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
				targets: `.${MENU_ITEM_CONTENT_CLASSNAME}`
			});

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDragItem.bind(this)
		);
		this._dragDrop.on(Drag.Events.START, this._handleDragStart.bind(this));
		this._dragDrop.on(DragDrop.Events.END, this._handleDropItem.bind(this));

		dom.on(
			`.${MENU_ITEM_CONTENT_CLASSNAME}`,
			'click',
			this._handleItemClick.bind(this)
		);

		dom.on(
			`.${MENU_ITEM_CONTENT_CLASSNAME}`,
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
	 * For a given item, returns it's menuItemContainer
	 * @param {HTMLElement} menuItem
	 * @private
	 * @return {HTMLElement}
	 * @review
	 */

	_getMenuItemContainer(menuItem) {
		return closest(menuItem, `.${MENU_ITEM_CLASSNAME}`) || menuItem;
	}

	/**
	 * For a given item, returns it's parent menuItem
	 * @param {HTMLElement} menuItem
	 * @private
	 * @return {HTMLElement}
	 * @review
	 */

	_getMenuItemParent(menuItem) {
		const itemContainer = this._getMenuItemContainer(menuItem);
		const itemContainerParent = itemContainer.parentNode;

		return match(itemContainerParent, `.${MENU_ITEM_CLASSNAME}`) ?
			itemContainerParent : itemContainerParent.querySelector(`.${MENU_ITEM_CONTENT_CLASSNAME}`);
	}

	/**
	 * For a given item, returns it's sibblings
	 * @param {HTMLElement} menuItem
	 * @private
	 * @return {Array<HTMLElement>}
	 * @review
	 */

	_getMenuItemSiblings(menuItem) {
		return Array.prototype
			.slice.call(this._getMenuItemContainer(this._getMenuItemParent(menuItem)).children)
			.filter(itemContainer => match(itemContainer, `.${MENU_ITEM_CLASSNAME}`))
			.map(itemContainer => itemContainer.querySelector(`.${MENU_ITEM_CONTENT_CLASSNAME}`));
	}

	/**
	 * This is called when user drags the item across the container.
	 *
	 * @param {!object} data Drag event data
	 * @param {!Event} event Drag event
	 * @private
	 */

	_handleDragItem(data, event) {
		const placeholder = data.placeholder;
		const source = data.source;
		const target = data.target;

		if (target &&
			target !== source &&
			!source.parentNode.contains(target) &&
			target.dataset.siteNavigationMenuItemId) {

			const placeholderRegion = position.getRegion(placeholder);
			const targetRegion = position.getRegion(target);

			const nested = placeholderRegion.right - targetRegion.right > placeholderRegion.width / 3;

			removeClasses(source.parentNode, MENU_ITEM_NESTED_CLASSNAME);

			let newParentId = target.dataset.parentSiteNavigationMenuItemId;

			if (placeholderRegion.top < targetRegion.top) {
				target.parentNode.parentNode.insertBefore(source.parentNode, target.parentNode);
			}
			else if (!nested && (placeholderRegion.bottom > targetRegion.bottom)) {
				target.parentNode.insertBefore(
					source.parentNode,
					target.nextSibling
				);
			}
			else if (nested && placeholderRegion.bottom > targetRegion.bottom) {
				target.parentNode.insertBefore(
					source.parentNode,
					target.nextSibling
				);

				newParentId = target.dataset.siteNavigationMenuItemId;

				addClasses(source.parentNode, MENU_ITEM_NESTED_CLASSNAME);
			}

			source.dataset.parentId = newParentId;

			const parent = document.querySelector(`[data-site-navigation-menu-item-id="${newParentId}"]`).parentNode;

			const children = Array.prototype.slice.call(parent.querySelectorAll(`.${MENU_ITEM_CLASSNAME}`))
				.filter(
					(node) =>
						(node === source.parentNode) ||
						(Array.prototype.slice.call(parent.children).indexOf(node) !== -1)
				);

			const order = children.reduce(
				(previousValue, currentValue, index) => {
					return currentValue === source.parentNode ? index : previousValue;
				},
				0
			);

			source.dataset.dragOrder = order;
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
		const item = event.target.getActiveDrag();

		addClasses(item.parentNode, MENU_ITEM_DRAGGING_CLASSNAME);
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

		if (data.source && data.source.dataset.siteNavigationMenuItemId) {
			this._updateParentAndOrder(data.source.dataset);
		}

		removeClasses(data.source.parentNode, MENU_ITEM_DRAGGING_CLASSNAME);
	}

	/**
	 * This is called when user clicks on menu item.
	 *
	 * @param {!Event} event Click event data
	 * @private
	 */

	_handleItemClick(event) {
		removeClasses(document.querySelectorAll(`.${MENU_ITEM_CONTENT_CLASSNAME}`), MENU_ITEM_SELECTED_CLASSNAME);

		addClasses(event.delegateTarget, MENU_ITEM_SELECTED_CLASSNAME);

		event.delegateTarget.parentNode.focus();
	}

	/**
	 * This is called when user presses a key on menu item.
	 *
	 * @param {!Event} event KeyUp event data
	 * @private
	 */

	_handleItemKeyUp(event) {
		const container = document.querySelector(`.${MENU_CONTAINER_CLASSNAME}`);
		const menuItem = event.delegateTarget.querySelector(`.${MENU_ITEM_CLASSNAME}`);
		const menuItemContainer = this._getMenuItemContainer(menuItem);
		const menuItemSiblings = this._getMenuItemSiblings(menuItem);

		const menuItemSelected = hasClass(menuItem, MENU_ITEM_SELECTED_CLASSNAME);

		const menuItemIndex = menuItemSiblings.indexOf(menuItem);

		const parentItemId = parseInt(menuItem.dataset.parentSiteNavigationMenuItemId, 10) || 0;

		const parentItem = container.querySelector(`[data-site-navigation-menu-item-id="${parentItemId}"]`);
		const parentItemContainer = this._getMenuItemContainer(parentItem);

		let layoutModified = false;

		if (event.key === KEYS.ENTER || event.key === KEYS.SPACEBAR) {
			menuItem.click();
		}
		else if (
			menuItemSelected &&
			(event.key === KEYS.ARROW_LEFT) &&
			(parentItemId > 0)
		) {
			const grandParentItem = this._getMenuItemParent(parentItem);
			const grandParentItemContainer = this._getMenuItemContainer(grandParentItem);
			const grandParentItemId = parseInt(grandParentItem.dataset.siteNavigationMenuItemId, 10) || 0;

			grandParentItemContainer.insertBefore(
				menuItemContainer,
				next(parentItemContainer, `.${MENU_ITEM_CLASSNAME}`)
			);

			menuItem.dataset.parentSiteNavigationMenuItemId = grandParentItemId.toString();

			if (grandParentItemId === 0) {
				removeClasses(
					menuItemContainer,
					MENU_ITEM_NESTED_CLASSNAME
				);
			}

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
			const menuItemSiblingContainer = this._getMenuItemContainer(menuItemSibling);
			const menuItemSiblingParentContainer = this._getMenuItemContainer(
				this._getMenuItemParent(
					menuItemSibling
				)
			);

			menuItemSiblingParentContainer.insertBefore(
				menuItemContainer,
				menuItemSiblingContainer
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
			const menuItemSiblingContainer = this._getMenuItemContainer(menuItemSibling);

			menuItemSiblingContainer.appendChild(menuItemContainer);
			addClasses(menuItemContainer, MENU_ITEM_NESTED_CLASSNAME);

			menuItem.dataset.parentSiteNavigationMenuItemId = menuItemSibling.dataset.siteNavigationMenuItemId;

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
				const menuItemSiblingContainer = this._getMenuItemContainer(menuItemSibling);
				const menuItemSiblingParentContainer = this._getMenuItemContainer(
					this._getMenuItemParent(menuItemSibling)
				);

				menuItemSiblingParentContainer.insertBefore(
					menuItemContainer,
					next(menuItemSiblingContainer, `.${MENU_ITEM_CLASSNAME}`)
				);
			}
			else {
				const menuItemParentContainer = this._getMenuItemContainer(
					this._getMenuItemParent(menuItem)
				);

				menuItemParentContainer.appendChild(menuItemContainer);
			}

			menuItem.dataset.order = newIndex.toString();

			layoutModified = true;
		}

		if (layoutModified) {
			this._updateParentAndOrder(
				{
					dragOrder: menuItem.dataset.order,
					parentId: menuItem.dataset.parentSiteNavigationMenuItemId,
					siteNavigationMenuItemId: menuItem.dataset.siteNavigationMenuItemId
				}
			);

			menuItem.click();
			menuItemContainer.focus();
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