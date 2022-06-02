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

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {url} = additionalProps;

			openSelectionModal({
				onSelect(selectedItem) {
					if (selectedItem) {
						const faviconCETPrimaryKey = document.getElementById(
							`${portletNamespace}faviconCETPrimaryKey`
						);

						const faviconFileEntryId = document.getElementById(
							`${portletNamespace}faviconFileEntryId`
						);
						const faviconFileEntryImage = document.getElementById(
							`${portletNamespace}faviconFileEntryImage`
						);
						const faviconFileEntryTitle = document.getElementById(
							`${portletNamespace}faviconFileEntryTitle`
						);

						if (
							selectedItem &&
							selectedItem.value &&
							faviconCETPrimaryKey &&
							faviconFileEntryId &&
							faviconFileEntryImage &&
							faviconFileEntryTitle
						) {
							const itemValue = JSON.parse(selectedItem.value);

							if (
								selectedItem.returnType ===
								'com.liferay.client.extension.type.item.selector.CETItemSelectorReturnType'
							) {
								faviconCETPrimaryKey.value =
									itemValue.cetPrimaryKey;
							}
							else {
								faviconFileEntryId.value =
									itemValue.fileEntryId;
							}

							if (itemValue.url) {
								faviconFileEntryImage.src = itemValue.url;
							}
							else {
								faviconFileEntryImage.classList.add('d-none');
							}

							faviconFileEntryTitle.innerHTML =
								itemValue.title || itemValue.name;
						}
					}
				},
				selectEventName: `${portletNamespace}selectImage`,
				title: Liferay.Language.get('select-favicon'),
				url: url.toString(),
			});
		},
	};
}
