import {
	actionItemsValidator,
	creationMenuItemsValidator,
	filterItemsValidator
} from 'clay-management-toolbar@2.0.0-rc.11/lib/validators';
import Component from 'metal-component@2.16.5/lib/Component';
import Config from 'metal-state@2.7.0/lib/Config';
import Soy from 'metal-soy@2.16.5/lib/Soy';

import {ManagementToolbar} from 'frontend-taglib-clay@1.0.0/management_toolbar/ManagementToolbar.es';

import templates from './ManagementToolbarWithExtraFilters.soy';

/**
 * Metal ManagementToolbar component.
 * @review
 */

class ManagementToolbarWithExtraFilters extends Component {

	created() {
		this._buildFilters();
	}

	/**
     * @inheritDoc
     */

	rendered() {
		AUI().use(
			'liferay-asset-taglib-categories-selector',
			function(A) {
				var categoryBox = this.element.querySelector('#assetCategoriesSelector');

				if (categoryBox) {
					const config = {
						categoryIds: '',
						categoryTitles: [],
						contentBox: categoryBox,
						eventName: this.namespace + 'selectCategory',
						groupIds: this.groupIds,
						hiddenInput: '#categoryIds',
						portletURL: this.categorySelectorURL,
						singleSelect: true,
						title: Liferay.Language.get('select-category'),
						vocabularyIds: this.vocabularyIds
					};

					this.categoriesSelector_ = new Liferay.AssetTaglibCategoriesSelector(config);

					const entries = this.categoriesSelector_.entries;

					entries.after('add', this.onEntriesChanged_, this);

					this.categoriesSelector_.render();
				}
			}.bind(this)
		);
	}

	onEntriesChanged_(event) {
		this._handleAddFilter();
	}

	_handleFilterChange(event) {
		var target = event.target;

		this._currentSelection = target.value;

		this._loadTerms();
	}

	_handleOptionChange(event) {
		var target = event.target;

		this._currentOption = target.value;

		this._loadOptionValues();
	}

	_getLabel(selection) {
		var label = '';

		if (this._currentSelection == 'optionsNames') {
			label = Liferay.Language.get('option');
		}
		else if (this._currentSelection == 'assetCategoryIds') {
			label = Liferay.Language.get('category');
		}
		else if (this._currentSelection == 'productTypeName') {
			label = Liferay.Language.get('product-type');
		}
		else if (this._currentSelection == 'status') {
			label = Liferay.Language.get('status');
		}

		return label;
	}

	_handleAddFilter() {
		var filters = this._filters;

		var field = this._currentSelection;
		var label = '';
		var value = '';

		if (this._currentSelection == 'optionsNames') {
			var optionNameSelect = this.element.querySelector('#optionsNames');
			var optionValueSelect = this.element.querySelector('#optionValues');

			var optionValue = optionValueSelect.value;

			var currentOptionName = optionNameSelect.options[optionNameSelect.selectedIndex];
			var currentOptionValue = optionValueSelect.options[optionValueSelect.selectedIndex];

			label = currentOptionName.getAttribute('data-label') + ' - ' + currentOptionValue.getAttribute('data-label');

			field = 'OPTION_' + this._currentOption;

			value = optionValue;
		}
		else if (this._currentSelection == 'assetCategoryIds') {
			var category = this.categoriesSelector_.entries.values[0];

			label = category.value;

			value = category.categoryId;
		}
		else {
			var currentSelect = this.element.querySelector('#' + this._currentSelection);

			var fieldValue = currentSelect.value;

			var currentOption = currentSelect.options[currentSelect.selectedIndex];

			label = currentOption.getAttribute('data-label');

			value = fieldValue;
		}

		label = this._getLabel(this._currentSelection) + ' : ' + label;

		filters.push(
			{
				field: field,
				label: label,
				value: value
			}
		);

		this._filters = filters;

		this._applyFilters();
	}

	_handlerRemoveFilter(event) {
		var target = event.target;

		// Chrome fix

		if (target.nodeName != 'button') {
			target = target.closest('button');
		}

		var index = target.getAttribute('data-index');

		var filters = this._filters;

		filters.splice(index, 1);

		this._filters = filters;

		this._applyFilters();
	}

	_buildFilters() {
		var url = new URL(this.portletURL);

		var filterFields = [];
		var filtersLabels = [];
		var filtersValues = [];

		var filterFieldsString = url.searchParams.get(this.namespace + 'filterFields');
		var filtersLabelsString = url.searchParams.get(this.namespace + 'filtersLabels');
		var filtersValuesString = url.searchParams.get(this.namespace + 'filtersValues');

		if (filterFieldsString) {
			filterFields = filterFieldsString.split(',');
		}

		if (filtersLabelsString) {
			filtersLabels = filtersLabelsString.split(',');
		}

		if (filtersValuesString) {
			filtersValues = filtersValuesString.split(',');
		}

		var filters = [];

		filterFields.forEach(
			(field, index) => {
				filters.push(
					{
						field: field,
						label: filtersLabels[index],
						value: filtersValues[index]
					}
				);
			}
		);

		this._filters = filters;
	}

	_applyFilters() {
		var url = new URL(this.portletURL);

		var filterFields = [];
		var filtersLabels = [];
		var filtersValues = [];

		this._filters.forEach(
			(filter) => {
				filterFields.push(filter.field);
				filtersLabels.push(filter.label);
				filtersValues.push(filter.value);
			}
		);

		url.searchParams.set(this.namespace + 'filterFields', filterFields.join(','));
		url.searchParams.set(this.namespace + 'filtersLabels', filtersLabels.join(','));
		url.searchParams.set(this.namespace + 'filtersValues', filtersValues.join(','));

		if (Liferay.SPA) {
			Liferay.SPA.app.navigate(url.toString());
		}
		else {
			window.location.href = url.toString();
		}
	}

	_loadTerms() {
		var url = new URL(this.cpDefinitionsFacetsURL);

		url.searchParams.append(this.namespace + 'fieldName', this._currentSelection);

		fetch(
			url,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this._terms = jsonResponse;
			}
		);
	}

	_loadOptionValues() {
		var url = new URL(this.cpDefinitionsFacetsURL);

		url.searchParams.append(this.namespace + 'fieldName', 'OPTION_' + this._currentOption);

		fetch(
			url,
			{
				credentials: 'include',
				method: 'GET'
			}
		).then(
			response => response.json()
		).then(
			(jsonResponse) => {
				this._optionValues = jsonResponse;
			}
		);
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */

ManagementToolbarWithExtraFilters.STATE = {

	/**
     * List of items to display in the actions menu on active state.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(array|undefined)}
     */

	actionItems: actionItemsValidator,

	/**
     * Url for clear results link.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	clearResultsURL: Config.string(),

	/**
     * Name of the content renderer to use template variants.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
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
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(object|string|bool|undefined)}
     */

	creationMenu: Config.shapeOf({
		caption: Config.string(),
		helpText: Config.string(),
		maxPrimaryItems: Config.number(),
		maxSecondaryItems: Config.number(),
		maxTotalItems: Config.number(),
		primaryItems: creationMenuItemsValidator,
		secondaryItems: creationMenuItemsValidator,
		viewMoreURL: Config.string()
	}),

	/**
     * Flag to indicate if the managment toolbar is disabled or not.
     * @default false
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	disabled: Config.bool().value(false),

	/**
     * CSS classes to be applied to the element.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	elementClasses: Config.string(),

	/**
     * List of filter menu items.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(array|undefined)}
     */

	filterItems: filterItemsValidator,

	/**
     * Id to be applied to the element.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	id: Config.string(),

	/**
     * Id to get the infoPanel node.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @review
     * @type {?string|undefined}
     */

	infoPanelId: Config.string(),

	/**
     * URL of the search form action
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	searchActionURL: Config.string(),

	/**
     * Id to get a instance of the searchContainer.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @review
     * @type {?string|undefined}
     */

	searchContainerId: Config.string(),

	/**
     * Name of the search form.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	searchFormName: Config.string(),

	/**
     * Name of the search input.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	searchInputName: Config.string(),

	/**
     * Value of the search input.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	searchValue: Config.string(),

	/**
     * Flag to indicate if the managment toolbar will control the selection of
     * elements.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(bool|undefined)}
     */

	selectable: Config.bool().value(false),

	/**
     * Number of selected items.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(number|undefined)}
     */

	selectedItems: Config.number(),

	/**
     * Flag to indicate if advanced search should be shown or not.
     * @default false
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	showAdvancedSearch: Config.bool().value(false),

	/**
     * Flag to indicate if creation menu button should be shown or not.
     * @default true
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	showCreationMenu: Config.bool().value(true),

	/**
     * Flag to indicate if the `Done` button in filter dropdown should be shown or
     * not.
     * @default true
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	showFiltersDoneButton: Config.bool().value(true),

	/**
     * Flag to indicate if the Info button should be shown or not.
     * @default false
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	showInfoButton: Config.bool().value(false),

	/**
     * Flag to indicate if search should be shown or not.
     * @default true
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?bool}
     */

	showSearch: Config.bool().value(true),

	/**
     * Sorting url.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	sortingURL: Config.string(),

	/**
     * Sorting order.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	sortingOrder: Config.oneOf(['asc', 'desc']),

	/**
     * The path to the SVG spritemap file containing the icons.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(string|undefined)}
     */

	spritemap: Config.string().required(),

	/**
     * Total number of items. If totalItems is 0 most of the elements in the bar
     * will appear disabled.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
     * @type {?(number|undefined)}
     */

	totalItems: Config.number(),

	/**
     * List of view items.
     * @default undefined
     * @instance
     * @memberof ManagementToolbarWithExtraFilters
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
	),

	categorySelectorURL: Config.string().required(),
	cpDefinitionsFacetsURL: Config.string().required(),
	groupIds: Config.string().value(''),
	namespace: Config.string().required(),
	portletURL: Config.string().required(),
	vocabularyIds: Config.string().required(),
	_currentSelection: Config.string().value('').internal(),
	_filters: Config.array().value([]).internal(),
	_optionValues: Config.array().value([]).internal(),
	_terms: Config.array().value([]).internal()
};

Soy.register(ManagementToolbarWithExtraFilters, templates);

export {ManagementToolbarWithExtraFilters};
export default ManagementToolbarWithExtraFilters;