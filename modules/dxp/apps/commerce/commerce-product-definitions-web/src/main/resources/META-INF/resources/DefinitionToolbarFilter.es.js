import Component from 'metal-component';
import { Config } from 'metal-state';
import Soy from 'metal-soy';
import {dom, globalEval} from 'metal-dom';
import { CancellablePromise } from 'metal-promise';
import { async, core } from 'metal';
import { RequestScreen, utils } from 'senna';
import Router from 'metal-router';

import templates from './DefinitionToolbarFilter.soy';

/**
 * DefinitionToolbarFilter
 *
 */
class DefinitionToolbarFilter extends Component {

	constructor(opt_config, opt_parentElement) {
		super(opt_config, opt_parentElement)

		this._buildFilters()
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
						hiddenInput:  '#categoryIds',
						singleSelect : true,
						portletURL: this.categorySelectorURL,
						vocabularyIds: this.vocabularyIds,
						title: 'Select Category'
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

	_handleAddFilter() {

		var filters = this._filters;

		if (this._currentSelection == "optionsNames") {

			var optionValueSelect = this.element.querySelector('#optionValues');

			var optionValue = optionValueSelect.value;

			var currentOptionValue =  optionValueSelect.options[optionValueSelect.selectedIndex];

			var label = currentOptionValue.getAttribute('data-label');

			filters.push(
				{
					field: "OPTION_" + this._currentOption,
					value: optionValue,
					label: label
				}
			);
		}
		else if(this._currentSelection == "assetCategoryIds") {

			var category = this.categoriesSelector_.entries.values[0];

			filters.push(
				{
					field: this._currentSelection,
					value: category.categoryId,
					label: category.value
				}
			);

		}
		else {

			var currentSelect = this.element.querySelector('#' + this._currentSelection);

			var fieldValue = currentSelect.value;

			var currentOption =  currentSelect.options[currentSelect.selectedIndex];

			var label = currentOption.getAttribute('data-label');

			filters.push(
				{
					field: this._currentSelection,
					value: fieldValue,
					label: label
				}
			);
		}

		this._filters = filters;

		this._applyFilters();
	}

	_handlerRemoveFilter(event) {

		var target = event.target;

		//Chrome Fix
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
			function(field, index) {
				filters.push(
					{
						field:field,
						value:filtersValues[index],
						label: filtersLabels[index]
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

		this._filters.forEach(function(filter) {
			filterFields.push(filter.field);
			filtersLabels.push(filter.label);
			filtersValues.push(filter.value);
		});

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

		url.searchParams.append(this.namespace + "fieldName", this._currentSelection);

		var promise = fetch(url, {
				credentials: 'include',
				method: 'GET',
			})
			.then(response => response.json())
			.then((jsonResponse) => {
				this._terms = jsonResponse;
			}
		);
	}

	_loadOptionValues() {

		var url = new URL(this.cpDefinitionsFacetsURL);

		url.searchParams.append(this.namespace + "fieldName", "OPTION_" + this._currentOption);

		var promise = fetch(url, {
				credentials: 'include',
				method: 'GET',
			})
			.then(response => response.json())
			.then((jsonResponse) => {
				this._optionValues = jsonResponse;
			}
		);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
DefinitionToolbarFilter.STATE = {

	categorySelectorURL: Config.string().required(),

	cpDefinitionsFacetsURL: Config.string().required(),

	groupIds: Config.string().value(''),

	namespace: Config.string().required(),

	pathThemeImages: Config.string().required(),

	portletURL: Config.string().required(),

	vocabularyIds: Config.string().required(),

	_currentSelection: Config.string().value(''),

	_filters: Config.array().value([]),

	_optionValues: Config.array().value([]),

	_terms: Config.array().value([])

};

// Register component
Soy.register(DefinitionToolbarFilter, templates);

export default DefinitionToolbarFilter;