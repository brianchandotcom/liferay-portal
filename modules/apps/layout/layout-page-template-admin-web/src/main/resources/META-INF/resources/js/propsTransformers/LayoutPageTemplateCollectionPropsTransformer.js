/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openSimpleInputModal} from 'frontend-js-web';

import openDeletePageTemplateModal from '../modal/openDeletePageTemplateModal';

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
			layoutPageTemplateCollectionName,
			updateLayoutPageTemplateCollectionURL,
		},
		portletNamespace
	) {
		openSimpleInputModal({
			dialogTitle,
			formSubmitURL: updateLayoutPageTemplateCollectionURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: layoutPageTemplateCollectionName,
			namespace: portletNamespace,
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
