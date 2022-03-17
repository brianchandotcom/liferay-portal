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

import {openModal, openSelectionModal} from 'frontend-js-web';

export const ACTIONS = {
	deleteCollections({
		deleteFragmentCollectionURL,
		portletNamespace,
		viewDeleteFragmentCollectionsURL,
	}) {
		this.openFragmentCollectionsItemSelector(
			Liferay.Language.get('delete'),
			Liferay.Language.get('delete-fragment-set'),
			viewDeleteFragmentCollectionsURL,
			(selectedItems) => {
				if (!selectedItems?.length) {
					return;
				}

				const form = document.getElementById(`${portletNamespace}fm`);

				if (!form) {
					return;
				}

				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						)
					)
				) {
					const input = document.createElement('input');

					input.name = `${portletNamespace}rowIds`;
					input.value = selectedItems.map((item) => item.value);

					form.appendChild(input);
				}

				submitForm(form, deleteFragmentCollectionURL);
			},
			null,
			portletNamespace
		);
	},

	exportCollections({
		exportFragmentCollectionsURL,
		portletNamespace,
		viewExportFragmentCollectionsURL,
	}) {
		let processed = false;

		this.openFragmentCollectionsItemSelector(
			Liferay.Language.get('export'),
			Liferay.Language.get('export-fragment-set'),
			viewExportFragmentCollectionsURL,
			(selectedItems) => {
				if (!selectedItems?.length) {
					return;
				}

				const form = document.getElementById(`${portletNamespace}fm`);

				if (!form) {
					return;
				}

				const input = document.createElement('input');

				input.name = `${portletNamespace}rowIds`;
				input.value = selectedItems.map((item) => item.value);
				input.setAttribute('type', 'hidden');

				form.appendChild(input);

				submitForm(form, exportFragmentCollectionsURL);

				form.removeChild(input);

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
			},
			portletNamespace
		);
	},

	openFragmentCollectionsItemSelector(
		dialogButtonLabel,
		dialogTitle,
		dialogURL,
		callback,
		onClose,
		portletNamespace
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
			selectEventName: `${portletNamespace}selectCollections`,
			title: dialogTitle,
			url: dialogURL,
		});
	},

	openImportView({portletNamespace, viewImportURL}) {
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
			id: `${portletNamespace}openImportView`,
			onClose: () => {
				window.location.reload();
			},
			title: Liferay.Language.get('import'),
			url: viewImportURL,
		});
	},
};
