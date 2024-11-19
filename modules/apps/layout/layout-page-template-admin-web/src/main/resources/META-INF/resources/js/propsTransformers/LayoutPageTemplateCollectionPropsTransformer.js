/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openCreationModal} from '@liferay/layout-js-components-web';
import {
	openModal,
	openSelectionModal,
	setFormValues,
	sub,
} from 'frontend-js-web';

import openDeletePageTemplateModal from '../commands/openDeletePageTemplateModal';

const ACTIONS = {
	copyLayoutPageTemplateCollection(
		{
			copySelectedEntriesURL,
			itemSelectorURL,
			layoutPageTemplateCollectionId,
			layoutPageTemplateCollectionName,
		},
		portletNamespace
	) {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItem) => {
				const form = document.getElementById(
					`${portletNamespace}actionEntriesFm`
				);

				setFormValues(form, {
					copyPermissions: true,
					layoutPageTemplateCollectionsIds:
						layoutPageTemplateCollectionId,
					layoutParentPageTemplateCollectionId:
						selectedItem.resourceid,
				});

				submitForm(form, copySelectedEntriesURL);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: sub(
				Liferay.Language.get('copy-x-to'),
				layoutPageTemplateCollectionName
			),
			url: itemSelectorURL,
		});
	},

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
			layoutPageTemplateCollectionId,
			layoutPageTemplateCollectionName,
			moveSelectedEntriesURL,
		},
		portletNamespace
	) {
		openSelectionModal({
			height: '70vh',
			onSelect: (selectedItem) => {
				const form = document.getElementById(
					`${portletNamespace}actionEntriesFm`
				);

				setFormValues(form, {
					layoutPageTemplateCollectionsIds:
						layoutPageTemplateCollectionId,
					layoutParentPageTemplateCollectionId:
						selectedItem.resourceid,
				});

				submitForm(form, moveSelectedEntriesURL);
			},
			selectEventName: 'selectFolder',
			size: 'md',
			title: sub(
				Liferay.Language.get('move-x-to'),
				layoutPageTemplateCollectionName
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
