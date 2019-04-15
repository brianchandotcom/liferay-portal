import 'clay-multi-select';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AssetTagsSelector.soy';

/**
 * AssetTagsSelector is a component wrapping the existing Clay's MultiSelect component
 * that offers the user a tag selection input
 */
class AssetTagsSelector extends Component {

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

	_handleButtonClicked() {
		AUI().use(
			'liferay-item-selector-dialog',
			function(A) {
				const uri = A.Lang.sub(
					decodeURIComponent(this.portletURL),
					{
						selectedTagNames: this._getTagNames()
					}
				);

				const itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: this.eventName,
						on: {
							selectedItemChange: function(event) {
								const selectedItems = event.newVal;

								if (selectedItems) {
									this.selectedItems = selectedItems.items.split(',').map(
										item => {
											return {
												label: item,
												value: item
											};
										}
									);
								}
							}.bind(this)
						},
						'strings.add': Liferay.Language.get('done'),
						title: Liferay.Language.get('tags'),
						url: uri
					}
				);

				itemSelectorDialog.open();
			}.bind(this)
		);
	}

	/**
	 * Converts the list of selected tags into a comma-separated serialized
	 * version to be used as a fallback for old services and implementations
	 * @private
	 * @return {string} The serialized, comma-separated version of the selected items
	 * @review
	 */
	_getTagNames() {
		return this.selectedItems.map(selectedItem => selectedItem.value).join();
	}

	/**
	 * Creates a tag with the text introduced in
	 * the input.
	 *
	 * @param  {!Event} event
	 */
	_handleInputBlur(event) {
		event.preventDefault();

		const inputValue = event.target.inputValue;

		if (inputValue) {
			const existingTag = this.selectedItems.find(tag => tag.value === inputValue);

			if (existingTag) {
				return;
			}

			const item = {
				label: inputValue,
				value: inputValue
			};

			this.selectedItems = this.selectedItems.concat(item);
			this.tagNames = this._getTagNames();

			if (this.addCallback) {
				window[this.addCallback](item);
			}

			this.emit(
				'itemAdded',
				{
					item: item,
					selectedItems: this.selectedItems
				}
			);
		}
	}

	_handleInputFocus(event) {
		this.emit('inputFocus', event);
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been added
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemAdded(event) {
		this.selectedItems = event.data.selectedItems;
		this.tagNames = this._getTagNames();

		if (this.addCallback) {
			window[this.addCallback](event.data.item);
		}

		this.emit(
			'itemAdded',
			{
				item: event.data.item,
				selectedItems: this.selectedItems
			}
		);
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been removed
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemRemoved(event) {
		this.selectedItems = event.data.selectedItems;
		this.tagNames = this._getTagNames();

		if (this.removeCallback) {
			window[this.removeCallback](event.data.item);
		}

		this.emit(
			'itemRemoved',
			{
				item: event.data.item,
				selectedItems: this.selectedItems
			}
		);
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
				Liferay.Service(
					'/assettag/search',
					{
						end: 20,
						groupIds: this.groupIds,
						name: `%${query === '*' ? '' : query}%`,
						start: 0,
						tagProperties: ''
					},
					tags => resolve(
						tags.map(tag => tag.value)
					)
				);
			}
		);
	}
}

AssetTagsSelector.STATE = {

	/**
	 * Function to be called every time that change the input value
	 * @default _handleQuery
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?func}
	 */
	_dataSource: Config.func().internal(),

	/**
	 * A function to call when a tag is added
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	addCallback: Config.string(),

	/**
	 * Event name which fires when user selects a display page using item selector
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	eventName: Config.string(),

	/**
	 * List of groupIds where tags should be located
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	groupIds: Config.array().value([]),

	/**
	 * The URL of a portlet to display the tags
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	portletURL: Config.string(),

	/**
	 * A function to call when a tag is removed
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	removeCallback: Config.string(),

	/**
	 * List of the selected Items.
	 * @default []
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?Array<Object>}
	 */

	selectedItems: Config.array(Config.object()).value([]),

	/**
	 * A comma separated version of the list of selected items
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @review
	 * @type {?string}
	 */

	tagNames: Config.string().value('')

};

Soy.register(AssetTagsSelector, templates);

export {AssetTagsSelector};
export default AssetTagsSelector;