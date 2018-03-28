import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './DefinitionToolbarFilter.soy';

/**
 * DefinitionToolbarFilter
 *
 */

class DefinitionToolbarFilter extends Component {

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