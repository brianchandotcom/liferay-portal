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

import {fetch, objectToFormData, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {eventName, getItemTypeURL, itemSelectorURL},
	portletNamespace,
	...props
}) {
	const url = new URL(itemSelectorURL);

	return {
		...props,
		onClick() {
			const classNameIdInput = document.getElementById(
				`${portletNamespace}classNameId`
			);

			const classPKInput = document.getElementById(
				`${portletNamespace}classPK`
			);

			const classTypeIdInput = document.getElementById(
				`${portletNamespace}classTypeId`
			);

			const itemSubtypeElement = document.getElementById(
				`${portletNamespace}itemSubtype`
			);

			const itemSubtypeLabel = document.getElementById(
				`${portletNamespace}itemSubtypeLabel`
			);

			const itemTypeLabel = document.getElementById(
				`${portletNamespace}itemTypeLabel`
			);

			const originalTitleInput = document.getElementById(
				`${portletNamespace}originalTitle`
			);

			const titleInput = document.getElementById(
				`${portletNamespace}title`
			);

			const typeInput = document.getElementById(
				`${portletNamespace}type`
			);

			openSelectionModal({
				onSelect: (selectedItem) => {
					if (selectedItem) {
						let infoItem = {
							...selectedItem,
						};

						let value;

						if (typeof selectedItem.value === 'string') {
							try {
								value = JSON.parse(selectedItem.value);
							}
							catch (error) {}
						}
						else if (
							selectedItem.value &&
							typeof selectedItem.value === 'object'
						) {
							value = selectedItem.value;
						}

						if (value) {
							delete infoItem.value;
							infoItem = {...value};
						}

						classNameIdInput.value = infoItem.classNameId;
						classPKInput.value = infoItem.classPK;
						classTypeIdInput.value = infoItem.classTypeId || 0;
						originalTitleInput.value = infoItem.title || '';
						titleInput.value = infoItem.title || '';
						typeInput.value = infoItem.type || '';

						const namespacedInfoItem = Liferay.Util.ns(
							portletNamespace,
							infoItem
						);

						fetch(getItemTypeURL, {
							body: objectToFormData(namespacedInfoItem),
							method: 'POST',
						})
							.then((response) => response.json())
							.then((jsonResponse) => {
								itemTypeLabel.innerHTML =
									jsonResponse.itemType || '';
								itemSubtypeLabel.innerHTML =
									jsonResponse.itemSubtype || '';

								itemSubtypeElement.classList.toggle(
									'd-none',
									!jsonResponse.itemSubtype
								);
							})
							.catch(() => {});
					}
				},
				selectEventName: eventName,
				title: Liferay.Language.get('select-item'),
				url: url.href,
			});
		},
	};
}
