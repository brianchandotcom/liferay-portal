/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import openDeleteFragmentCollectionResourceModal from './openDeleteFragmentCollectionResourceModal';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'deleteSelectedFragmentCollectionResources') {
				openDeleteFragmentCollectionResourceModal({
					multiple: true,
					onDelete: () => {
						const form = document.getElementById(
							`${portletNamespace}fm`
						);

						if (form) {
							submitForm(
								form,
								data?.deleteFragmentCollectionResourcesURL
							);
						}
					},
				});
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'addFragmentCollectionResource') {
				openSelectionModal({
					onSelect(selectedItem) {
						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							const fileEntryIdElement = document.getElementById(
								`${portletNamespace}fileEntryId`
							);

							if (fileEntryIdElement) {
								fileEntryIdElement.setAttribute(
									'value',
									itemValue.fileEntryId
								);
							}

							const form = document.getElementById(
								`${portletNamespace}fragmentCollectionResourceFm`
							);

							if (form) {
								submitForm(form);
							}
						}
					},
					selectEventName: `${portletNamespace}uploadFragmentCollectionResource`,
					title: Liferay.Language.get('upload-fragment-set-resource'),
					url: data.itemSelectorURL,
				});
			}
		},
	};
}
