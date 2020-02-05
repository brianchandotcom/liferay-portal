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

import {ItemSelectorDialog, PortletBase} from 'frontend-js-web';
import {Config} from 'metal-state';

/**
 * @class FragmentCollectionsView
 */
class FragmentCollectionsView extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	created() {
		this._fragmentCollectionsFm =
			document[this.ns('fragmentCollectionsFm')];
		this._handleComponentReady = this._handleComponentReady.bind(this);

		Liferay.componentReady(this.ns('actionsComponent')).then(
			this._handleComponentReady
		);

		Liferay.componentReady(this.ns('emptyResultMessageComponent')).then(
			this._handleComponentReady
		);
	}

	/**
	 * Handles a componentReady event and adds a click handler to the
	 * given fragment.
	 * @param {{ on: function }} component
	 * @private
	 * @review
	 */
	_handleComponentReady(component) {
		const ACTIONS = {
			deleteCollections: this._deleteCollections,
			exportCollections: this._exportCollections,
			openImportView: this._openImportView
		};

		component.on(['click', 'itemClicked'], (event, facade) => {
			let itemData;

			if (event.data && event.data.item) {
				itemData = event.data.item.data;
			}
			else if (!event.data && facade && facade.target) {
				itemData = facade.target.data;
			}

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action].call(this);
			}
		});
	}

	/**
	 * Opens an item selector to remove selected collections
	 * @private
	 * @review
	 */
	_deleteCollections() {
		this._openFragmentCollectionsItemSelector(
			Liferay.Language.get('delete'),
			Liferay.Language.get('delete-collection'),
			this.viewDeleteFragmentCollectionsURL,
			selectedItems => {
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						)
					)
				) {
					selectedItems.forEach(item => {
						this._fragmentCollectionsFm.appendChild(item);
					});
				}

				submitForm(
					this._fragmentCollectionsFm,
					this.deleteFragmentCollectionURL
				);
			}
		);
	}

	/**
	 * Opens an item selector to export selected collections
	 * @private
	 * @review
	 */
	_exportCollections() {
		this._openFragmentCollectionsItemSelector(
			Liferay.Language.get('export'),
			Liferay.Language.get('export-collection'),
			this.viewExportFragmentCollectionsURL,
			selectedItems => {
				selectedItems.forEach(item => {
					this._fragmentCollectionsFm.append(item);
				});

				submitForm(
					this._fragmentCollectionsFm,
					this.exportFragmentCollectionsURL
				);
			}
		);
	}

	/**
	 * Opens an item selector to select some fragment collections.
	 * @param {string} dialogButtonLabel
	 * @param {string} dialogTitle
	 * @param {string} dialogURL
	 * @param {function} callback Callback executed when some items have been
	 *  selected. They will be sent as parameters to this callback
	 * @private
	 * @review
	 */
	_openFragmentCollectionsItemSelector(
		dialogButtonLabel,
		dialogTitle,
		dialogURL,
		callback
	) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: dialogButtonLabel,
			eventName: this.ns('selectCollections'),
			title: dialogTitle,
			url: dialogURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			if (event.selectedItem) {
				callback(event.selectedItem);
			}
		});

		itemSelectorDialog.open();
	}

	/**
	 * Opens an iframe dialog with an fragment collection import form
	 * @private
	 * @review
	 */
	_openImportView() {
		Liferay.Util.openWindow({
			dialog: {
				after: {
					destroy: () => {
						window.location.reload();
					}
				},
				destroyOnHide: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			id: this.ns('openImportView'),
			title: Liferay.Language.get('import'),
			uri: this.viewImportURL
		});
	}
}

FragmentCollectionsView.STATE = {
	/**
	 * Collections form used for updating backend
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {HTMLFormElement|undefined}
	 */
	_fragmentCollectionsFm: Config.instanceOf(HTMLFormElement).internal(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {string}
	 */
	deleteFragmentCollectionURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {string}
	 */
	exportFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {string}
	 */
	viewDeleteFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {string}
	 */
	viewExportFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsView
	 * @review
	 * @type {string}
	 */
	viewImportURL: Config.string().required()
};

export default FragmentCollectionsView;
