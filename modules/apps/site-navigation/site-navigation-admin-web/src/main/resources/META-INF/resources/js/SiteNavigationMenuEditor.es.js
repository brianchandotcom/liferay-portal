import {dom} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';
import State, {Config} from 'metal-state';
import throttle from 'metal-throttle';

import {
	getNearestMenuItem,
	insertAtPosition,
	isOver,
	shouldBeNested
} from './SiteNavigationMenuDOMHandler.es';

import {
	getChildren,
	getFromContentElement,
	getFromId,
	getId,
	getParent,
	getSiblings,
	isMenuItem,
	MENU_ITEM_CLASSNAME,
	MENU_ITEM_CONTENT_CLASSNAME,
	MENU_ITEM_DRAG_ICON_CLASSNAME,
	setDragging,
	setSelected,
	unselectAll
} from './SiteNavigationMenuItemDOMHandler.es';

/**
 * Document height.
 * @review
 */

const DOCUMENT_HEIGHT = document.body.offsetHeight;

/**
 * List of keys used for moving elements with keyboard.
 * @review
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
 * Distance the window moves on scroll.
 * @review
 */

const SCROLL_DISPLACEMENT = 100;

/**
 * Window height
 * @review
 */

const WINDOW_HEIGHT = window.innerHeight;

/**
 * Margin on window top and bottom in which scroll starts.
 * @review
 */

const SCROLL_MARGIN = WINDOW_HEIGHT * 0.2;

/**
 * SiteNavigationMenuEditor
 * @review
 */

class SiteNavigationMenuEditor extends State {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor(config, ...args) {
		super(config, ...args);

		const controlMenu = document.querySelector('.control-menu');
		this._controlMenuHeight = controlMenu ? controlMenu.offsetHeight : 0;

		const managementBar = document.querySelector('.management-bar');
		this._managementBarHeight = managementBar ? managementBar.offsetHeight : 0;

		this._scrollOnDrag = throttle(this._scrollOnDrag.bind(this), 250);
		this._scrollOnDragLoop = this._scrollOnDragLoop.bind(this);

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
	 * @review
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
	 * Scrolls up or down when an item is being dragged.
	 * @private
	 * @review
	 */

	_scrollOnDrag() {
		if (
			this._currentYPosition < this._draggedItemRegion.top &&
			this._draggedItemRegion.top > (WINDOW_HEIGHT - SCROLL_MARGIN) &&
			(this._draggedItemRegion.bottom + window.scrollY) < (DOCUMENT_HEIGHT + this._draggedItemRegion.height)
		) {
			window.scrollTo(
				{
					behavior: 'smooth',
					top: window.scrollY + SCROLL_DISPLACEMENT
				}
			);
		}
		else if (
			this._currentYPosition > this._draggedItemRegion.top &&
			this._draggedItemRegion.top < (this._controlMenuHeight + this._managementBarHeight + SCROLL_MARGIN)
		) {
			window.scrollTo(
				{
					behavior: 'smooth',
					top: window.scrollY - SCROLL_DISPLACEMENT
				}
			);
		}

		this._currentYPosition = this._draggedItemRegion.top;
	}

	/**
	 * Animates the scroll when an item is being dragged.
	 * @private
	 * @review
	 */

	_scrollOnDragLoop() {
		this._scrollOnDrag(this._draggedItemRegion);
		this._scrollAnimationId = requestAnimationFrame(this._scrollOnDragLoop);
	}

	/**
	 * This is called when user drags the item across the container.
	 * @param {!object} data Drag event data
	 * @param {!Event} event Drag event
	 * @private
	 * @review
	 */

	_handleDragItem(data, event) {
		const placeholderMenuItem = data.placeholder;
		const sourceMenuItem = data.source;

		const nearestMenuItem = getNearestMenuItem(
			sourceMenuItem,
			placeholderMenuItem
		);

		if (!this._draggedItemRegion) {
			this._draggedItemRegion = position.getRegion(placeholderMenuItem);
			this._scrollOnDragLoop();
		}

		this._draggedItemRegion = position.getRegion(placeholderMenuItem);

		if (
			placeholderMenuItem && isMenuItem(placeholderMenuItem) &&
			sourceMenuItem && isMenuItem(sourceMenuItem) &&
			nearestMenuItem && isMenuItem(nearestMenuItem)
		) {
			const nested = shouldBeNested(
				placeholderMenuItem,
				nearestMenuItem
			);

			const over = isOver(
				placeholderMenuItem,
				nearestMenuItem
			);

			if (!over && nested) {
				insertAtPosition(
					nearestMenuItem,
					sourceMenuItem,
					0
				);
			}
			else {
				const nearestMenuItemParent = getParent(
					nearestMenuItem
				);

				const nearestMenuItemIndex = getChildren(nearestMenuItemParent).indexOf(nearestMenuItem) + (over ? 0 : 1);

				insertAtPosition(
					nearestMenuItemParent,
					sourceMenuItem,
					nearestMenuItemIndex
				);
			}
		}
	}

	/**
	 * This is called when user starts to drag the item across the container.
	 * @param {!object} data Drag event data
	 * @param {!Event} event Drag event
	 * @private
	 * @review
	 */

	_handleDragStart(data, event) {
		const menuItem = event.target.getActiveDrag();

		this.selectedMenuItem = menuItem;

		setDragging(menuItem, true);
	}

	/**
	 * This is called when user drops the item on the container.
	 * @param {!object} data Drop event data
	 * @param {!Event} event Drop event
	 * @private
	 * @review
	 */

	_handleDropItem(data, event) {
		event.preventDefault();

		const menuItem = data.source;
		const menuItemId = getId(menuItem);

		const menuItemIndex = getSiblings(menuItem)
			.indexOf(menuItem);

		const menuItemParentId = getId(
			getParent(menuItem)
		);

		cancelAnimationFrame(this._scrollAnimationId);
		this._scrollAnimationId = -1;
		this._draggedItemRegion = null;

		this._updateParentAndOrder(
			{
				dragOrder: menuItemIndex,
				parentId: menuItemParentId,
				siteNavigationMenuItemId: menuItemId
			}
		);

		setDragging(menuItem, false);
	}

	/**
	 * This is called when user clicks on menu item.
	 * @param {!Event} event Click event data
	 * @private
	 * @review
	 */

	_handleItemClick(event) {
		const menuItem = getFromContentElement(
			event.delegateTarget
		);

		this.selectedMenuItem = menuItem;
	}

	/**
	 * This is called when user presses a key on menu item.
	 * @param {!Event} event KeyUp event data
	 * @private
	 * @review
	 */

	_handleItemKeyUp(event) {
		event.stopPropagation();

		const menuItem = event.delegateTarget;
		const menuItemIndex = getSiblings(menuItem)
			.indexOf(menuItem);

		const menuItemParent = getParent(menuItem);

		let layoutModified = false;

		if ((event.key === KEYS.ENTER) || (event.key === KEYS.SPACEBAR)) {
			this.selectedMenuItem = menuItem;
		}
		else if (event.key === KEYS.ARROW_LEFT) {
			const menuItemParentIndex = getSiblings(menuItemParent)
				.indexOf(menuItemParent);

			const menuItemGrandParent = getParent(
				menuItemParent
			);

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
			insertAtPosition(
				menuItemParent,
				menuItem,
				menuItemIndex - 1
			);

			layoutModified = true;
		}
		else if (event.key === KEYS.ARROW_RIGHT && menuItemIndex > 0) {
			const previousSibling = getSiblings(menuItem)[menuItemIndex - 1];

			insertAtPosition(
				previousSibling,
				menuItem,
				Infinity
			);

			layoutModified = true;
		}
		else if (event.key === KEYS.ARROW_DOWN) {
			insertAtPosition(
				menuItemParent,
				menuItem,
				menuItemIndex + 2
			);

			layoutModified = true;
		}

		if (layoutModified) {
			const siteNavigationMenuItemId = getId(
				menuItem
			);

			this._updateParentAndOrder(
				{
					dragOrder: getSiblings(menuItem)
						.indexOf(menuItem),

					parentId: getId(
						getParent(menuItem)
					),

					siteNavigationMenuItemId
				}
			);

			requestAnimationFrame(
				() => {
					const modifiedMenuItem = getFromId(
						siteNavigationMenuItemId
					);

					modifiedMenuItem.focus();
				}
			);
		}
	}

	/**
	 * Handle selectedMenuItem property change.
	 * @param {{newVal: HTMLElement|null}} event
	 * @private
	 * @review
	 */

	_handleSelectedMenuItemChanged(event) {
		unselectAll();

		if (event.newVal) {
			setSelected(event.newVal);
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
	 * @review
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
 * @review
 * @static
 * @type {!Object}
 */

SiteNavigationMenuEditor.STATE = {

	/**
	 * URL for edit site navigation menu item parent action.
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @review
	 * @type {!string}
	 */

	editSiteNavigationMenuItemParentURL: Config.string().required(),

	/**
	 * Portlet namespace to use in edit action.
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @review
	 * @type {!string}
	 */

	namespace: Config.string().required(),

	/**
	 * Selected menuItem DOM element.
	 * @default null
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @review
	 * @type {HTMLElement}
	 */

	selectedMenuItem: Config.object().value(null),

	/**
	 * Control menu height.
	 * @default 0
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @review
	 * @type {number}
	 */

	_controlMenuHeight: Config.number().internal().value(0),

	/**
	 * @default -1
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @review
	 * @type {number}
	 */

	_currentYPosition: Config.number().internal().value(-1),

	/**
	 * Internal DragDrop instance.
	 * @default null
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @review
	 * @type {object|null}
	 */

	_dragDrop: Config.internal().value(null),

	/**
	 * Dragged item.
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @review
	 * @type {HTMLElement|null}
	 */

	_draggedItemRegion: Config.object().internal(),

	/**
	 * Management bar height
	 * @default 0
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @review
	 * @type {number}
	 */

	_managementBarHeight: Config.number().internal().value(0),

	/**
	 * @default -1
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @private
	 * @review
	 * @type {number}
	 */

	_scrollAnimationId: Config.number().internal().value(-1)

};

export {SiteNavigationMenuEditor};
export default SiteNavigationMenuEditor;