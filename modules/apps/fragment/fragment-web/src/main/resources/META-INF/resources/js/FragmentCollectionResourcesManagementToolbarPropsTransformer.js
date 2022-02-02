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

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'deleteSelectedFragmentCollectionResources') {
				if (
					confirm(
						Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this'
						)
					)
				) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(
							form,
							data?.deleteFragmentCollectionResourcesURL
						);
					}
				}
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
