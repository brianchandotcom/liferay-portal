import {
	actionItemsValidator,
	creationMenuItemsValidator,
	filterItemsValidator,
	filterLabelsValidator
} from 'clay-management-toolbar';
import ClayComponent from 'clay-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {EventEmitterProxy} from 'metal-events';

import templates from './ManagementToolbar.soy';

/**
 * Metal ManagementToolbar component.
 * @review
 */

class ManagementToolbar extends ClayComponent {

	/**
	 * @inheritDoc
	 * @review
	 */

	attached(...args) {
		super.attached(...args);

		new EventEmitterProxy(this.refs.managementToolbar, this);

		Liferay.componentReady(this.searchContainerId).then(
			searchContainer => {
				this._eventHandler = [
					searchContainer.on('rowToggled', this._handleSearchContainerRowToggled, this)
				];

				this._searchContainer = searchContainer;
			}
		);

		if (this.actionHandler) {
			Liferay.componentReady(this.actionHandler).then(
				actionHandler => {
					this.defaultEventHandler = actionHandler;
				}
			);
		}

		if (this.infoPanelId) {
			let sidenavToggle = AUI.$(this.refs.managementToolbar.refs.infoButton);

			if (!sidenavToggle.sideNavigation('instance')) {
				sidenavToggle.sideNavigation(
					{
						container: '#' + this.infoPanelId,
						position: 'right',
						type: 'relative',
						typeMobile: 'fixed',
						width: '320px'
					}
				);

				this._sidenavInstance = sidenavToggle.sideNavigation('instance');
			}
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	disposed(...args) {
		super.disposed(...args);

		if (this._eventHandler) {
			this._eventHandler.forEach(
				eventHandler => {
					eventHandler.detach();
				}
			);
		}
	}

	/**
	 * Deselects all searchContainer rows
	 * @param {!Event} event
	 * @private
	 */

	_handleDeselectAllClicked() {
		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(false);
		}
	}

	/**
	 * Toggles the info panel
	 * @param {!Event} event
	 * @private
	 */

	_handleInfoButtonClicked(event) {
		event.preventDefault();
		event.stopPropagation();

		if (this._sidenavInstance) {
			this._sidenavInstance.toggle();
		}
	}

	/**
	 * Toggles all searchContainer rows
	 * @param {!Event} event
	 * @private
	 */

	_handleSelectPageCheckboxChanged(event) {
		if (this._searchContainer) {
			let checkboxStatus = event.data.checked;

			if (checkboxStatus) {
				this._searchContainer.select.toggleAllRows(true);
			}
			else {
				this._searchContainer.select.toggleAllRows(false);
			}
		}
	}

	/**
	 * Selects all searchContainer rows
	 * @param {!Event} event
	 * @private
	 */

	_handleSelectAllClicked(event) {
		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(true);
		}
	}

	/**
	 * Updates management toolbar selectedItems count on searchContainer element
	 * toggled.
	 * @param {object} event The row toggle event from the SearchContainer instance
	 * @private
	 * @review
	 */

	_handleSearchContainerRowToggled(event) {
		var elements = event.elements;

		this.selectedItems = elements.allSelectedElements.filter(':enabled').size();

		if (this.actionItems) {
			this.actionItems = this.actionItems.map(
				actionItem => {
					return Object.assign(
						actionItem,
						{
							disabled: event.actions && !event.actions.includes(actionItem.data.action)
						}
					);
				}
			);
		}
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */

ManagementToolbar.STATE = {

	/**
	 * Component wired to handle the different available user actions in the
	 * ManagementToolbar component.
	 *
	 * The actionHandler should be a string that represent a component ID that
	 * the toolbar can resolve through a `Liferay.componentReady(actionHandler)`
	 * call.
	 *
	 * @default undefined
	 * @deprecated use defaultEventHandler instead
	 * @instance
	 * @memberof ManagementToolbar
	 * @review
	 * @type {?(string|object|undefined)}
	 */

	actionHandler: Config.string(),

	/**
	 * List of items to display in the actions menu on active state.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */

	actionItems: actionItemsValidator,

	/**
	 * Url for clear results link.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	clearResultsURL: Config.string(),

	/**
	 * Name of the content renderer to use template variants.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	contentRenderer: Config.string(),

	/**
	 * Configuration of the creation menu.
	 * Set `true` to render a plain button that will emit an event onclick.
	 * Set `string` to use it as link href to render a link styled button.
	 * Set `object` to render a dropdown menu with items.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(object|string|bool|undefined)}
	 */

	creationMenu: Config.shapeOf(
		{
			caption: Config.string(),
			helpText: Config.string(),
			maxPrimaryItems: Config.number(),
			maxSecondaryItems: Config.number(),
			maxTotalItems: Config.number(),
			primaryItems: creationMenuItemsValidator,
			secondaryItems: creationMenuItemsValidator,
			viewMoreURL: Config.string()
		}
	),

	/**
	 * Component wired to handle the different available user actions in the
	 * ManagementToolbar component.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @review
	 * @type {?(string|object|undefined)}
	 */

	defaultEventHandler: Config.object(),

	/**
	 * Flag to indicate if the managment toolbar is disabled or not.
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	disabled: Config.bool().value(false),

	/**
	 * CSS classes to be applied to the element.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	elementClasses: Config.string(),

	/**
	 * List of filter menu items.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */

	filterItems: filterItemsValidator,

	/**
	 * List of filter label items.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */

	filterLabels: filterLabelsValidator,

	/**
	 * Id to be applied to the element.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	id: Config.string(),

	/**
	 * Id to get the infoPanel node.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @review
	 * @type {?string|undefined}
	 */

	infoPanelId: Config.string(),

	/**
	 * URL of the search form action
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	searchActionURL: Config.string(),

	/**
	 * Map of properties that will be rendered as hidden inputs in the search
	 * form.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?object}
	 */

	searchData: Config.object(),

	/**
	 * Method of the search form.
	 * @default GET
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	searchFormMethod: Config.oneOf(['GET', 'POST']).value('GET'),

	/**
	 * Id to get a instance of the searchContainer.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @review
	 * @type {?string|undefined}
	 */

	searchContainerId: Config.string(),

	/**
	 * Name of the search form.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	searchFormName: Config.string(),

	/**
	 * Name of the search input.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	searchInputName: Config.string(),

	/**
	 * Value of the search input.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	searchValue: Config.string(),

	/**
	 * Flag to indicate if the managment toolbar will control the selection of
	 * elements.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(bool|undefined)}
	 */

	selectable: Config.bool().value(false),

	/**
	 * Number of selected items.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(number|undefined)}
	 */

	selectedItems: Config.number(),

	/**
	 * Flag to indicate if advanced search should be shown or not.
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	showAdvancedSearch: Config.bool().value(false),

	/**
	 * Flag to indicate if creation menu button should be shown or not.
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	showCreationMenu: Config.bool().value(true),

	/**
	 * Flag to indicate if the `Done` button in filter dropdown should be shown or
	 * not.
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	showFiltersDoneButton: Config.bool().value(true),

	/**
	 * Flag to indicate if the Info button should be shown or not.
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	showInfoButton: Config.bool().value(false),

	/**
	 * Flag to indicate if search should be shown or not.
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */

	showSearch: Config.bool().value(true),

	/**
	 * Sorting url.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	sortingURL: Config.string(),

	/**
	 * Sorting order.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	sortingOrder: Config.oneOf(['asc', 'desc']),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */

	spritemap: Config.string().required(),

	/**
	 * Total number of items. If totalItems is 0 most of the elements in the bar
	 * will appear disabled.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(number|undefined)}
	 */

	totalItems: Config.number(),

	/**
	 * List of view items.
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */

	viewTypes: Config.arrayOf(
		Config.shapeOf(
			{
				active: Config.bool().value(false),
				disabled: Config.bool().value(false),
				href: Config.string(),
				icon: Config.string().required(),
				label: Config.string().required()
			}
		)
	)
};

Soy.register(ManagementToolbar, templates);

export {ManagementToolbar};
export default ManagementToolbar;