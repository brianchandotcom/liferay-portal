import State, {Config} from 'metal-state';
import {addClasses, dom, removeClasses} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';
import position from 'metal-position';

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

		this._dragDrop = new DragDrop({
			dragPlaceholder: Drag.Placeholder.CLONE,
			handles: '.sticker',
			sources: this.menuItemSelector,
			targets: `${this.menuContainerSelector} ${this.menuItemSelector}`,
		});

		this._dragDrop.on(
			DragDrop.Events.DRAG,
			this._handleDragItem.bind(this)
		);
		this._dragDrop.on(Drag.Events.START, this._handleDragStart.bind(this));
		this._dragDrop.on(DragDrop.Events.END, this._handleDropItem.bind(this));

		dom.on(
			this.menuItemSelector,
			'click',
			this._handleItemClick.bind(this)
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
		const placeholder = data.placeholder;
		const source = data.source;
		const target = data.target;

		if (
			!target ||
			target === source ||
			source.parentNode.contains(target)
		) {
			return;
		}

		const placeholderRegion = position.getRegion(placeholder);
		const targetRegion = position.getRegion(target);

		if (!target.dataset.siteNavigationMenuItemId) {
			return;
		}

		const nested =
			placeholderRegion.right - targetRegion.right >
			placeholderRegion.width / 3;

		removeClasses(source.parentNode, 'ml-5');

		let newParentId = target.dataset.parentSiteNavigationMenuItemId;

		if (placeholderRegion.top < targetRegion.top) {
			target.parentNode.parentNode.insertBefore(
				source.parentNode, target.parentNode);
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

			addClasses(source.parentNode, 'ml-5');
		}

		source.dataset.parentId = newParentId;

		const parent = document.querySelector(
			`[data-site-navigation-menu-item-id="${newParentId}"]`).parentNode;

		const children = Array.from(parent.querySelectorAll('.container-item'))
			.filter(
				(node) =>
					(node === source.parentNode) ||
					(Array.from(parent.children).indexOf(node) != -1)
			);

		const order = children.reduce((previousValue, currentValue, index) => {
			return currentValue === source.parentNode ? index : previousValue;
		}, 0);

		source.dataset.dragOrder = order;
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

		addClasses(item.parentNode, 'item-dragging');
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
			const formData = new FormData();

			formData.append(
				`${this.namespace}siteNavigationMenuItemId`,
				data.source.dataset.siteNavigationMenuItemId
			);
			formData.append(
				`${this.namespace}parentSiteNavigationMenuItemId`,
				data.source.dataset.parentId
			);
			formData.append(
				`${this.namespace}order`,
				data.source.dataset.dragOrder
			);

			fetch(this.editSiteNavigationMenuItemParentURL, {
				body: formData,
				credentials: 'include',
				method: 'POST',
			}).then(() => {
				if (Liferay.SPA) {
					Liferay.SPA.app.navigate(window.location.href);
				}
				else {
					window.location.reload();
				}
			});
		}
		else {
			removeClasses(data.source.parentNode, 'item-dragging');
		}
	}

	/**
	 * This is called when user clicks on menu item.
	 *
	 * @param {!Event} event Click event data
	 * @private
	 */
	_handleItemClick(event) {
		removeClasses(document.querySelectorAll('.selected'), 'selected');

		addClasses(event.delegateTarget, 'selected');
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
	 * Selector to get site navigation menu container.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */
	menuContainerSelector: Config.string().required(),

	/**
	 * Selector to get all site navigation menu items.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf SiteNavigationMenuEditor
	 * @type {!string}
	 */
	menuItemSelector: Config.string().required(),

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
	_dragDrop: Config.internal().value(null),
};

export {SiteNavigationMenuEditor};
export default SiteNavigationMenuEditor;
