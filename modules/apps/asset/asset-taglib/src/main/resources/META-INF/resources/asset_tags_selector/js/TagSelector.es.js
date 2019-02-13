import 'clay-multi-select';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';

import templates from './TagSelector.soy';

/**
 * TagSelector is a component wrapping the existing Clay's MultiSelect component
 * that offers the user a tag selection input
 */
class TagSelector extends Component {

	/**
	 * @inheritDoc
	 */

	attached(...args) {
		super.attached(...args);

		this.refs.multiSelect.dataSource = this._handleQuery.bind(this);
	}

	/**
	 * Opens the dialog tag selection
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleButtonClicked() {
		const selectedTagNames = this.selectedItems.map(
			item => item.value
		).join();

		AUI().use(
			'liferay-item-selector-dialog',
			function(A) {
				const uri = A.Lang.sub(
					decodeURIComponent(this.portletURL),
					{
						selectedTagNames: selectedTagNames
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
	 * Updates tags fallback and notifies that a new tag has been added
	 * @param {!Event} event
	 * @private
	 * @review
	 */

	_handleItemAdded(event) {
		this._updateSelectedItemsFallback();

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
		this._updateSelectedItemsFallback();

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
						groupIds: [themeDisplay.getScopeGroupId()],
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

	/**
	 * Updates the fallback element with a serialized version of the currently
	 * selected tags in case calling code expects a single input tag with the
	 * comma-separated version of the selected items
	 * @private
	 * @review
	 */

	_updateSelectedItemsFallback() {
		document.getElementById(this.inputName).setAttribute(
			'value',
			this.selectedItems.map(selectedItem => selectedItem.value)
		);
	}
}

TagSelector.STATE = {

	/**
	 * A function to call when a tag is added
	 * @default undefined
	 * @instance
	 * @memberof TagSelector
	 * @review
	 * @type {?string}
	 */

	addCallback: Config.string(),

	/**
	 * Event name which fires when user selects a display page using item selector
	 * @default undefined
	 * @instance
	 * @memberof TagSelector
	 * @review
	 * @type {?string}
	 */

	eventName: Config.string(),

	/**
	 * The URL of a portlet to display the tags
	 * @default undefined
	 * @instance
	 * @memberof TagSelector
	 * @review
	 * @type {?string}
	 */

	portletURL: Config.string(),

	/**
	 * A function to call when a tag is removed
	 * @default undefined
	 * @instance
	 * @memberof TagSelector
	 * @review
	 * @type {?string}
	 */

	removeCallback: Config.string()
};

Soy.register(TagSelector, templates);

export {TagSelector};
export default TagSelector;