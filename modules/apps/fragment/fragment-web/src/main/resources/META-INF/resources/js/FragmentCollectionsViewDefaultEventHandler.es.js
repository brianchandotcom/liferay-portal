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

import {
	DefaultEventHandler,
	openModal,
	openSelectionModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

/**
 * @class FragmentCollectionsViewDefaultEventHandler
 */
class FragmentCollectionsViewDefaultEventHandler extends DefaultEventHandler {

	/**
	 * Opens an item selector to remove selected collections
	 * @private
	 * @review
	 */
	deleteCollections() {
		this._openFragmentCollectionsItemSelector(
			Liferay.Language.get('delete'),
			Liferay.Language.get('delete-collection'),
			this.viewDeleteFragmentCollectionsURL,
			(selectedItems) => {
				const fragmentCollectionsForm = document.getElementById(
					this.ns('fragmentCollectionsFm')
				);

				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						)
					)
				) {
					selectedItems.forEach((item) => {
						fragmentCollectionsForm.appendChild(
							item.cloneNode(true)
						);
					});
				}

				submitForm(
					fragmentCollectionsForm,
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
	exportCollections() {
		let processed = false;

		this._openFragmentCollectionsItemSelector(
			Liferay.Language.get('export'),
			Liferay.Language.get('export-collection'),
			this.viewExportFragmentCollectionsURL,
			(selectedItems) => {
				const fragmentCollectionsForm = document.getElementById(
					this.ns('fragmentCollectionsFm')
				);

				selectedItems.forEach((item) => {
					fragmentCollectionsForm.appendChild(item.cloneNode(true));
				});

				submitForm(
					fragmentCollectionsForm,
					this.exportFragmentCollectionsURL
				);

				processed = true;
			},
			() => {
				if (processed) {
					Liferay.Util.openToast({
						message: Liferay.Language.get(
							'your-request-processed-successfully'
						),
						toastProps: {
							autoClose: 5000,
						},
						type: 'success',
					});
				}
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
		callback,
		onClose
	) {
		openSelectionModal({
			buttonAddLabel: dialogButtonLabel,
			multiple: true,
			onClose,
			onSelect: (selectedItem) => {
				if (selectedItem) {
					callback(selectedItem);
				}
			},
			selectEventName: this.ns('selectCollections'),
			title: dialogTitle,
			url: dialogURL,
		});
	}

	/**
	 * Opens an iframe dialog with an fragment collection import form
	 * @private
	 * @review
	 */
	openImportView() {
		openModal({
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					type: 'cancel',
				},
				{
					label: Liferay.Language.get('import'),
					type: 'submit',
				},
			],
			id: this.ns('openImportView'),
			onClose: () => {
				window.location.reload();
			},
			title: Liferay.Language.get('import'),
			url: this.viewImportURL,
		});
	}
}

FragmentCollectionsViewDefaultEventHandler.STATE = {

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsViewDefaultEventHandler
	 * @review
	 * @type {string}
	 */
	deleteFragmentCollectionURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsViewDefaultEventHandler
	 * @review
	 * @type {string}
	 */
	exportFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsViewDefaultEventHandler
	 * @review
	 * @type {string}
	 */
	viewDeleteFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsViewDefaultEventHandler
	 * @review
	 * @type {string}
	 */
	viewExportFragmentCollectionsURL: Config.string().required(),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FragmentCollectionsViewDefaultEventHandler
	 * @review
	 * @type {string}
	 */
	viewImportURL: Config.string().required(),
};

export default FragmentCollectionsViewDefaultEventHandler;
