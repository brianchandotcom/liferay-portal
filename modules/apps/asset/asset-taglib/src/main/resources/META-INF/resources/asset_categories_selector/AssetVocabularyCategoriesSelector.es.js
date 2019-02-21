import 'clay-multi-select';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AssetVocabularyCategoriesSelector.soy';

/**
 * AssetVocabularyCategoriesSelector is a component wrapping the existing Clay's MultiSelect
 * component that offers the user a categories selection input
 */
class AssetVocabularyCategoriesSelector extends Component {

	/**
	 * @inheritDoc
	 */

	attached(...args) {
		super.attached(...args);

		this._dataSource = this._handleQuery.bind(this);
	}

	/**
	 * Opens the dialog tag selection
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleButtonClicked(event) {
		AUI().use(
			'liferay-item-selector-dialog',
			function(A) {
				var uri = A.Lang.sub(
					decodeURIComponent(this.portletURL),
					{
						selectedCategories: this.selectedItems.map(
							item => item.value
						).join(),
						singleSelect: this.singleSelect,
						vocabularyIds: this.vocabularyId
					}
				);

				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.eventName,
						on: {
							selectedItemChange: function(event) {
								const selectedItems = event.newVal;

								if (selectedItems) {
									this.selectedItems = Object.keys(selectedItems).map(
										itemKey => {
											return {
												label: selectedItems[itemKey].value,
												value: selectedItems[itemKey].categoryId
											};
										}
									);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('add'),
						title: Liferay.Language.get('select-categories'),
						url: uri
					}
				);

				itemSelectorDialog.open();
			}.bind(this)
		);
	}

	/**
	 * Converts the list of selected categories into a comma-separated serialized
	 * version to be used as a fallback for old services and implementations
	 * @private
	 * @return {string} The serialized, comma-separated version of the selected items
	 * @review
	 */
	_getCategoryIds() {
		return this.selectedItems.map(selectedItem => selectedItem.value).join();
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been added
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemAdded(event) {
		const multiSelect = this.refs.multiSelect;

		const match = multiSelect.filteredItems.find(
			item => {
				return (event.data.item.label === item.data.label);
			}
		);

		if (match) {
			const selectedItems = multiSelect.selectedItems;

			selectedItems[selectedItems.length - 1].value = match.data.value;
		}

		this.categoryIds = this._getCategoryIds();
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been removed
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemRemoved(event) {
		this.categoryIds = this._getCategoryIds();
	}

	/**
	 * Responds to user input to retrieve the list of available tags from the
	 * tags search service
	 * @param {!string} query
	 * @private
	 * @review
	 */

	_handleQuery(query) {
		return new Promise(
			(resolve, reject) => {
				const serviceOptions = {
					end: 20,
					groupId: themeDisplay.getScopeGroupId(),
					keywords: query,
					start: 0,
					vocabularyId: this.vocabularyId
				};

				serviceOptions['-obc'] = null;

				Liferay.Service(
					'/assetcategory/search',
					serviceOptions,
					categories => resolve(
						categories.map(
							category => {
								return {
									label: category.titleCurrentValue,
									value: category.categoryId
								};
							}
						)
					)
				);
			}
		);
	}
}

AssetVocabularyCategoriesSelector.STATE = {

	/**
	 * A comma separated version of the list of selected items
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @review
	 * @type {?string}
	 */

	categoryIds: Config.string().value(''),

	/**
	 * Event name which fires when user selects a display page using item selector
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @review
	 * @type {?string}
	 */

	eventName: Config.string(),

	/**
	 * The URL of a portlet to display the tags
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @review
	 * @type {?string}
	 */

	portletURL: Config.string(),

	/**
	 * A function to call when a tag is removed
	 * @default undefined
	 * @instance
	 * @memberof AssetVocabularyCategoriesSelector
	 * @review
	 * @type {?string}
	 */

	selectedItems: Config.array().value([]),

	singleSelect: Config.bool(),

	vocabularyId: Config.string()
};

Soy.register(AssetVocabularyCategoriesSelector, templates);

export {AssetVocabularyCategoriesSelector};
export default AssetVocabularyCategoriesSelector;