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

import ClayComponent from 'clay-component';
import {
	actionItemsValidator,
	creationMenuItemsValidator,
	filterItemsValidator,
	filterLabelsValidator
} from 'clay-management-toolbar';
import {EventEmitterProxy} from 'metal-events';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './ManagementToolbar.soy';

/**
 * Creates a Metal Management Toolbar component.
 */
class ManagementToolbar extends ClayComponent {
	/**
	 * @inheritDoc
	 */
	attached(...args) {
		super.attached(...args);

		new EventEmitterProxy(this.refs.managementToolbar, this);

		Liferay.componentReady(this.searchContainerId).then(searchContainer => {
			this._eventHandler = [
				searchContainer.on(
					'rowToggled',
					this._handleSearchContainerRowToggled,
					this
				)
			];

			this._searchContainer = searchContainer;

			const select = searchContainer.select;

			if (
				select &&
				select.getAllSelectedElements &&
				select.getCurrentPageElements &&
				select.getCurrentPageSelectedElements
			) {
				const bulkSelection =
					this.supportsBulkActions && select.get('bulkSelection');

				this._setActiveStatus(
					{
						allSelectedElements: select.getAllSelectedElements(),
						currentPageElements: select.getCurrentPageElements(),
						currentPageSelectedElements: select.getCurrentPageSelectedElements()
					},
					bulkSelection
				);
			}
		});

		if (this.infoPanelId) {
			const sidenavToggle = this.refs.managementToolbar.refs.infoButton;

			if (sidenavToggle) {
				Liferay.SideNavigation.initialize(sidenavToggle, {
					container: '#' + this.infoPanelId,
					position: 'right',
					type: 'relative',
					typeMobile: 'fixed',
					width: '320px'
				});
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	disposed(...args) {
		super.disposed(...args);

		if (this.infoPanelId) {
			const sidenavToggle = this.refs.managementToolbar.refs.infoButton;

			if (sidenavToggle) {
				Liferay.SideNavigation.destroy(sidenavToggle);
			}
		}

		if (this._eventHandler) {
			this._eventHandler.forEach(eventHandler => {
				eventHandler.detach();
			});
		}
	}

	isCacheable(uri) {
		let cacheable = true;

		if (this._searchContainer && this._searchContainer.select) {
			const keepSelection = this._searchContainer.select.get(
				'keepSelection'
			);

			cacheable = keepSelection.test(uri);
		}

		return cacheable;
	}

	/**
	 * Deselects all search container rows.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleClearSelectionButtonClicked() {
		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(false, true);
		}
	}

	_handleCreationMenuMoreButtonClicked() {
		const creationMenuPrimaryItemsCount = this.creationMenu.primaryItems
			? this.creationMenu.primaryItems.length
			: 0;

		const creationMenuFavoriteItems =
			this.creationMenu.secondaryItems &&
			this.creationMenu.secondaryItems[0]
				? this.creationMenu.secondaryItems[0].items
				: [];
		const creationMenuRestItems =
			this.creationMenu.secondaryItems &&
			this.creationMenu.secondaryItems[1]
				? this.creationMenu.secondaryItems[1].items
				: [];

		const creationMenuSecondaryItemsCount =
			creationMenuFavoriteItems.length + creationMenuRestItems.length;
		const creationMenuTotalItemsCount =
			creationMenuPrimaryItemsCount + creationMenuSecondaryItemsCount;

		this.creationMenu.maxPrimaryItems = creationMenuPrimaryItemsCount;
		this.creationMenu.maxSecondaryItems = creationMenuSecondaryItemsCount;
		this.creationMenu.maxTotalItems = creationMenuTotalItemsCount;

		this.refs.managementToolbar.refs.creationMenuDropdown.maxPrimaryItems = creationMenuPrimaryItemsCount;
		this.refs.managementToolbar.refs.creationMenuDropdown.maxSecondaryItems = creationMenuSecondaryItemsCount;
		this.refs.managementToolbar.refs.creationMenuDropdown.maxTotalItems = creationMenuTotalItemsCount;
	}

	_handleFilterLabelCloseClicked(event) {
		const removeLabelURL =
			event.data.label.data && event.data.label.data.removeLabelURL;

		if (removeLabelURL) {
			Liferay.Util.navigate(removeLabelURL);
		}
	}

	/**
	 * Toggles all search container rows.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleSelectPageCheckboxChanged(event) {
		if (this._searchContainer) {
			const checkboxStatus = event.data.checked;

			if (checkboxStatus) {
				this._searchContainer.select.toggleAllRows(true);
			}
			else {
				this._searchContainer.select.toggleAllRows(false);
			}
		}
	}

	/**
	 * Selects all search container rows.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleSelectAllButtonClicked() {
		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(true, true);
		}
	}

	/**
	 * Updates the count for the selected items in the management toolbar when
	 * the search container element is toggled.
	 *
	 * @param {object} event The row toggle event from the search container.
	 * @private
	 */
	_handleSearchContainerRowToggled(event) {
		const actions = event.actions;
		const bulkSelection =
			this.supportsBulkActions &&
			this._searchContainer.select.get('bulkSelection');

		this._setActiveStatus(event.elements, bulkSelection);

		if (this.actionItems) {
			this.actionItems = this.actionItems.map(actionItem => {
				return Object.assign(actionItem, {
					disabled:
						actions &&
						actions.indexOf(actionItem.data.action) === -1 &&
						(!bulkSelection || !actionItem.data.enableOnBulk)
				});
			});
		}
	}

	/**
	 * Updates the management toolbar's active status checkbox.
	 *
	 * @param {object} elements The list of elements.
	 * @param {bool} bulkSelection Whether bulk selection is enabled.
	 * @private
	 */
	_setActiveStatus(elements, bulkSelection) {
		const currentPageElements = elements.currentPageElements.size();
		const currentPageSelectedElements = elements.currentPageSelectedElements.size();

		const currentPageSelected =
			currentPageElements === currentPageSelectedElements;

		this.selectedItems = bulkSelection
			? this.totalItems
			: elements.allSelectedElements.filter(':enabled').size();
		this.active = this.selectedItems > 0;

		if (currentPageSelectedElements > 0) {
			this.checkboxStatus = currentPageSelected
				? 'checked'
				: 'indeterminate';
		}
		else {
			this.checkboxStatus = 'unchecked';
		}

		if (this.supportsBulkActions) {
			this.showSelectAllButton =
				currentPageSelected &&
				this.totalItems > this.selectedItems &&
				!this._searchContainer.select.get('bulkSelection');
		}
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
ManagementToolbar.STATE = {
	/**
	 * List of items to display in the actions menu on active state.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */
	actionItems: actionItemsValidator,

	/**
	 * Status of the select items checkbox. If the status is checked or
	 * indeterminate, the toolbar is in active state.
	 *
	 * @default unchecked
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	checkboxStatus: Config.oneOf([
		'checked',
		'indeterminate',
		'unchecked'
	]).value('unchecked'),

	/**
	 * URL for the clear results link.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	clearResultsURL: Config.string(),

	/**
	 * URL for the clear selection link.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	clearSelectionURL: Config.string(),

	/**
	 * Name of the content renderer to use template variants.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	contentRenderer: Config.string(),

	/**
	 * Configuration of the creation menu.
	 *
	 * <ul>
	 * <li>
	 * Set <code>true</code> to render a plain button that emits an event on
	 * click.
	 * </li>
	 * <li>
	 * Set <code>string</code> to use it as link <code>href</code> to render a
	 * link styled button.
	 * </li>
	 * <li>
	 * Set <code>object</code> to render a dropdown menu with items.
	 * </li>
	 * </ul>
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(object|string|bool|undefined)}
	 */
	creationMenu: Config.shapeOf({
		caption: Config.string(),
		helpText: Config.string(),
		itemsIconAlignment: Config.string(),
		maxPrimaryItems: Config.number(),
		maxSecondaryItems: Config.number(),
		maxTotalItems: Config.number(),
		primaryItems: creationMenuItemsValidator,
		secondaryItems: creationMenuItemsValidator,
		viewMoreURL: Config.string()
	}),

	/**
	 * Component wired to handle the available user actions in the Management
	 * Toolbar component.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|object|undefined)}
	 */
	defaultEventHandler: Config.object(),

	/**
	 * Flag to indicate if the managment toolbar is disabled.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	disabled: Config.bool().value(false),

	/**
	 * CSS classes to be applied to the element.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	elementClasses: Config.string(),

	/**
	 * List of filter menu items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */
	filterItems: filterItemsValidator,

	/**
	 * List of filter label items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */
	filterLabels: filterLabelsValidator,

	/**
	 * ID to apply to the element.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	id: Config.string(),

	/**
	 * ID to get the info panel node.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?string|undefined}
	 */
	infoPanelId: Config.string(),

	/**
	 * URL of the search form action.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	searchActionURL: Config.string(),

	/**
	 * ID to get an instance of the search container.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?string|undefined}
	 */
	searchContainerId: Config.string(),

	/**
	 * Map of properties that are rendered as hidden inputs in the search form.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?object}
	 */
	searchData: Config.object(),

	/**
	 * Method of the search form.
	 *
	 * @default GET
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	searchFormMethod: Config.oneOf(['GET', 'POST']).value('GET'),

	/**
	 * Name of the search form.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	searchFormName: Config.string(),

	/**
	 * Name of the search input.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	searchInputName: Config.string(),

	/**
	 * Value of the search input.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	searchValue: Config.string(),

	/**
	 * URL for the Select All link.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	selectAllURL: Config.string(),

	/**
	 * Flag to indicate if the managment toolbar controls the selection of
	 * elements.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(bool|undefined)}
	 */
	selectable: Config.bool().value(false),

	/**
	 * Number of selected items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(number|undefined)}
	 */
	selectedItems: Config.number(),

	/**
	 * Flag to indicate if the advanced search is visible.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showAdvancedSearch: Config.bool().value(false),

	/**
	 * Flag to indicate if the creation menu button is visible.
	 *
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showCreationMenu: Config.bool().value(true),

	/**
	 * Flag to indicate if the Done button in the filter dropdown is visible.
	 *
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showFiltersDoneButton: Config.bool().value(true),

	/**
	 * Flag to indicate if the Info button is visible.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showInfoButton: Config.bool().value(false),

	/**
	 * Flag to indicate if the results bar is visible.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showResultsBar: Config.bool().value(false),

	/**
	 * Flag to indicate if search is visible.
	 *
	 * @default true
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showSearch: Config.bool().value(true),

	/**
	 * Flag to indicate if the Select All button is visible.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?bool}
	 */
	showSelectAllButton: Config.bool().value(false),

	/**
	 * Sorting order.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	sortingOrder: Config.oneOf(['asc', 'desc']),

	/**
	 * Sorting URL.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	sortingURL: Config.string(),

	/**
	 * Path to the SVG spritemap file containing the icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(string|undefined)}
	 */
	spritemap: Config.string().required(),

	/**
	 * Flag to indicate if the toolbar supports bulk selection.
	 *
	 * @default false
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {boolean}
	 */
	supportsBulkActions: Config.bool().value(false),

	/**
	 * Total number of items. If <code>0</code>, most of the elements in the
	 * toolbar are disabled.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(number|undefined)}
	 */
	totalItems: Config.number(),

	/**
	 * List of view items.
	 *
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?(array|undefined)}
	 */
	viewTypes: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			disabled: Config.bool().value(false),
			href: Config.string(),
			icon: Config.string().required(),
			label: Config.string().required()
		})
	)
};

Soy.register(ManagementToolbar, templates);

export {ManagementToolbar};
export default ManagementToolbar;
