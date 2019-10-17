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

import 'clay-multi-select';
import {ItemSelectorDialog} from 'frontend-js-web';
import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './AssetTagsSelector.soy';

/**
 * Wraps Clay's existing <code>MultiSelect</code> component that offers the user
 * a tag selection input.
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
	 * Opens the tag selection dialog.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleButtonClicked() {
		const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

		const uri = sub(decodeURIComponent(this.portletURL), {
			selectedTagNames: this._getTagNames()
		});

		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('done'),
			eventName: this.eventName,
			title: Liferay.Language.get('tags'),
			url: uri
		});

		itemSelectorDialog.open();

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItems = event.selectedItem;

			if (selectedItems) {
				const newValues =
					selectedItems.items.length > 0
						? selectedItems.items.split(',')
						: [];
				const oldItems = this.selectedItems.slice();
				const oldValues = oldItems.map(item => item.value);
				const valueMapper = item => {
					return {
						label: item,
						value: item
					};
				};

				const addedItems = newValues
					.filter(value => !oldValues.includes(value))
					.map(valueMapper);

				const removedItems = oldValues
					.filter(value => !newValues.includes(value))
					.map(valueMapper);

				this.selectedItems = newValues.map(valueMapper);

				this.tagNames = this._getTagNames();

				addedItems.forEach(item =>
					this._notifyItemsChanged(
						'itemAdded',
						this.addCallback,
						item
					)
				);

				removedItems.forEach(item =>
					this._notifyItemsChanged(
						'itemRemoved',
						this.removeCallback,
						item
					)
				);
			}
		});
	}

	/**
	 * Converts the list of selected tags into a comma-separated serialized
	 * version to be used as a fallback for old services and implementations.
	 *
	 * @private
	 * @return {string} The serialized, comma-separated version of the selected items.
	 */
	_getTagNames() {
		return this.selectedItems
			.map(selectedItem => selectedItem.value)
			.join();
	}

	/**
	 * Creates a tag with the text introduced in the input.
	 *
	 * @param  {!Event} event The event.
	 */
	_handleInputBlur(event) {
		const filteredItems = event.target.filteredItems;

		if (!filteredItems || (filteredItems && filteredItems.length === 0)) {
			const inputValue = event.target.inputValue;

			if (inputValue) {
				const existingTag = this.selectedItems.find(
					tag => tag.value === inputValue
				);

				if (existingTag) {
					return;
				}

				const item = {
					label: inputValue,
					value: inputValue
				};

				this.selectedItems = this.selectedItems.concat(item);
				this.tagNames = this._getTagNames();

				this._notifyItemsChanged('itemAdded', this.addCallback, item);
			}
		}
	}

	_handleInputFocus(event) {
		this.emit('inputFocus', event);
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been added.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleItemAdded(event) {
		this.selectedItems = event.data.selectedItems;
		this.tagNames = this._getTagNames();

		this._notifyItemsChanged(
			'itemAdded',
			this.addCallback,
			event.data.item
		);
	}

	/**
	 * Updates tags fallback and notifies that a new tag has been removed.
	 *
	 * @param {!Event} event The event.
	 * @private
	 */
	_handleItemRemoved(event) {
		this.selectedItems = event.data.selectedItems;
		this.tagNames = this._getTagNames();

		this._notifyItemsChanged(
			'itemRemoved',
			this.removeCallback,
			event.data.item
		);
	}

	/**
	 * Responds to user input to retrieve the list of available tags from the
	 * tags search service.
	 *
	 * @param {!string} query
	 * @private
	 */
	_handleQuery(query) {
		return new Promise(resolve => {
			Liferay.Service(
				'/assettag/search',
				{
					end: 20,
					groupIds: this.groupIds,
					name: `%${query === '*' ? '' : query}%`,
					start: 0,
					tagProperties: ''
				},
				tags => resolve(tags.map(tag => tag.value))
			);
		});
	}

	/**
	 * Notifies changed items
	 *
	 * @param {!string} eventName
	 * @param {!Function} callback
	 * @param {!object} item
	 * @private
	 */
	_notifyItemsChanged(eventName, callback, item) {
		if (callback) {
			window[callback](item);
		}

		this.emit(eventName, {
			item,
			selectedItems: this.selectedItems
		});
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
AssetTagsSelector.STATE = {
	/**
	 * Function to call every time the input value changes.
	 *
	 * @default _handleQuery
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?func}
	 */
	_dataSource: Config.func().internal(),

	/**
	 * Function to call when a tag is added.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?string}
	 */
	addCallback: Config.string(),

	/**
	 * Event name which fires when the user selects a display page using the
	 * item selector.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?string}
	 */
	eventName: Config.string(),

	/**
	 * List of group IDs where tags should be located.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?string}
	 */
	groupIds: Config.array().value([]),

	/**
	 * URL of a portlet to display the tags.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?string}
	 */
	portletURL: Config.string(),

	/**
	 * Function to call when a tag is removed.
	 *
	 * @default undefined
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?string}
	 */
	removeCallback: Config.string(),

	/**
	 * List of the selected items.
	 *
	 * @default []
	 * @instance
	 * @memberof AssetTagsSelector
	 * @type {?Array<Object>}
	 */
	selectedItems: Config.array(Config.object()).value([]),

	/**
	 * A comma separated list of selected items.
	 *
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
