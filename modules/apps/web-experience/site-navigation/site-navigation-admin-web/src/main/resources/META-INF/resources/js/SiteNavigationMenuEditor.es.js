import State, {Config} from 'metal-state';
import {addClasses, dom, removeClasses} from 'metal-dom';
import {Drag, DragDrop} from 'metal-drag-drop';

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
			cloneContainer: document.body,
			constrain: this.menuContainerSelector,
			dragPlaceholder: Drag.Placeholder.CLONE,
			handles: '.sticker',
			sources: this.menuItemSelector,
			targets: [this.menuContainerSelector, this.menuItemSelector].join(),
		});

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
	 * This is called when user clicks on menu item.
	 *
	 * @param {!Event} event Click event data
	 * @private
	 */
	_handleItemClick(event) {
		removeClasses(document.querySelectorAll('.selected'), 'selected');

		addClasses(event.delegateTarget, 'selected');
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

		if (
			data.source &&
			data.target &&
			data.source.dataset.sitenavigationmenuitemid &&
			data.source.dataset.sitenavigationmenuitemid !==
				data.target.dataset.sitenavigationmenuitemid
		) {
			const formData = new FormData();

			formData.append(
				`${this.namespace}siteNavigationMenuItemId`,
				data.source.dataset.sitenavigationmenuitemid
			);
			formData.append(
				`${this.namespace}parentSiteNavigationMenuItemId`,
				data.target.dataset.sitenavigationmenuitemid
			);

			fetch(this.editSiteNavigationMenuItemParentURL, {
				body: formData,
				credentials: 'include',
				method: 'POST',
			}).then(
				() => {
					if (Liferay.SPA) {
						Liferay.SPA.app.navigate(window.location.href);
					}
					else {
						window.location.reload();
					}
				}
			);
		}
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
