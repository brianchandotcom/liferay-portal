import 'clay-multi-select';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './AssetTagsSelector.soy';

/**
 * AssetTagSelector is a component wrapping the existing Clay's MultiSelect component
 * that offers the user a tag selection input
 */
class AssetTagSelector extends Component {

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
	 * Updates tags fallback and notifies that a new tag has been added
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemAdded(event) {
		this.tagNames = this._getTagNames();

		if (this.addCallback) {
			window[this.addCallback](event.data.item);
		}
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been removed
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemRemoved(event) {
		this.tagNames = this._getTagNames();

		if (this.removeCallback) {
			window[this.removeCallback](event.data.item);
		}
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

AssetTagSelector.STATE = {

	/**
	 * Function to be called every time that change the input value
	 * @default _handleQuery
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?func}
	 */
	_dataSource: Config.func().internal(),

	/**
	 * A function to call when a tag is added
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	addCallback: Config.string(),

	/**
	 * Event name which fires when user selects a display page using item selector
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	eventName: Config.string(),

	/**
	 * List of groupIds where tags should be located
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	groupIds: Config.array(),

	/**
	 * The URL of a portlet to display the tags
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	portletURL: Config.string(),

	/**
	 * A function to call when a tag is removed
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	removeCallback: Config.string(),

	/**
	 * A comma separated version of the list of selected items
	 * @default undefined
	 * @instance
	 * @memberof AssetTagSelector
	 * @review
	 * @type {?string}
	 */

	tagNames: Config.string()
};

Soy.register(AssetTagSelector, templates);

export {AssetTagSelector};
export default AssetTagSelector;