/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

import openDeletePageTemplateModal from './modal/openDeletePageTemplateModal';

const ACTIONS = {
	deleteCollections({
		deleteLayoutPageTemplateCollectionURL,
		portletNamespace,
		viewLayoutPageTemplateCollectionURL,
	}) {
		const layoutPageTemplateCollectionsForm = document.createElement(
			'form'
		);

		layoutPageTemplateCollectionsForm.setAttribute('method', 'post');

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('delete'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems) {
					openDeletePageTemplateModal({
						onDelete: () => {
							const input = document.createElement('input');

							input.name = `${portletNamespace}rowIds`;
							input.value = selectedItems.map(
								(item) => item.value
							);

							layoutPageTemplateCollectionsForm.appendChild(
								input
							);

							submitForm(
								layoutPageTemplateCollectionsForm,
								deleteLayoutPageTemplateCollectionURL
							);
						},
						title: Liferay.Language.get('page-template-sets'),
					});
				}
			},
			selectEventName: `${portletNamespace}selectCollections`,
			title: Liferay.Language.get('delete-collection'),
			url: viewLayoutPageTemplateCollectionURL,
		});
	},
};

export default function propsTransformer({
	additionalProps: {
		deleteLayoutPageTemplateCollectionURL,
		viewLayoutPageTemplateCollectionURL,
	},
	items,
	portletNamespace,
	...otherProps
}) {
	return {
		...otherProps,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action]({
							deleteLayoutPageTemplateCollectionURL,
							portletNamespace,
							viewLayoutPageTemplateCollectionURL,
						});
					}
				},
			};
		}),
	};
}
