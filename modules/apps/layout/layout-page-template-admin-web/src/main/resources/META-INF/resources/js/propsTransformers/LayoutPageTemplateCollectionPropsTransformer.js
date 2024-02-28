/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCreationModal} from '@liferay/layout-js-components-web';
import {
	fetch,
	objectToFormData,
	openModal,
	openSelectionModal,
	openToast,
	sub,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';

const ACTIONS = {
	deleteLayoutPageTemplateCollection({
		deleteLayoutPageTemplateCollectionURL,
		dialogTitle,
	}) {
		openDeletePageTemplateModal({
			onDelete: () => {
				submitForm(
					document.hrefFm,
					deleteLayoutPageTemplateCollectionURL
				);
			},
			title: dialogTitle,
		});
	},

	moveLayoutPageTemplateCollection(
		{
			itemSelectorURL,
			layoutPageTemplateCollectionName,
			moveLayoutPageTemplateCollectionURL,
		},
		portletNamespace
	) {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItems) => {
				fetch(moveLayoutPageTemplateCollectionURL, {
					body: objectToFormData({
						[`${portletNamespace}targetLayoutPageTemplateCollectionId`]: selectedItems.resourceid,
					}),
					method: 'POST',
				})
					.then((response) => {
						if (!response.ok) {
							throw new Error();
						}

						window.location.reload();
					})
					.catch(
						({
							message = Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						}) => {
							openToast({
								message,
								type: 'danger',
							});
						}
					);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: sub(
				Liferay.Language.get('move-x-to'),
				`"${layoutPageTemplateCollectionName}"`
			),
			url: itemSelectorURL,
		});
	},

	permissionsLayoutPageTemplateCollection({
		permissionsLayoutPageTemplateCollectionURL,
	}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsLayoutPageTemplateCollectionURL,
		});
	},

	updateLayoutPageTemplateCollection(
		{
			dialogTitle,
			layoutPageTemplateCollectionDescription,
			layoutPageTemplateCollectionName,
			updateLayoutPageTemplateCollectionURL,
		},
		portletNamespace
	) {
		openCreationModal({
			descriptionInputValue: layoutPageTemplateCollectionDescription,
			formSubmitURL: updateLayoutPageTemplateCollectionURL,
			heading: dialogTitle,
			nameInputValue: layoutPageTemplateCollectionName,
			portletNamespace,
		});
	},
};

const updateItem = (item, portletNamespace) => {
	const newItem = {
		...item,
		onClick(event) {
			const action = item.data?.action;

			if (action) {
				event.preventDefault();

				ACTIONS[action]?.(item.data, portletNamespace);
			}
		},
	};

	if (Array.isArray(item.items)) {
		newItem.items = item.items.map((item) =>
			updateItem(item, portletNamespace)
		);
	}

	return newItem;
};

export default function LayoutPageTemplateEntryPropsTransformer({
	actions,
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		actions: actions?.map((item) => updateItem(item, portletNamespace)),
		items: items?.map((item) => updateItem(item, portletNamespace)),
		portletNamespace,
	};
}
