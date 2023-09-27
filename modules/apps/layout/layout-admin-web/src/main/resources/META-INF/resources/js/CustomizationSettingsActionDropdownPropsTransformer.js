/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

const ACTIONS = {
	resetCustomizationView(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-reset-your-customizations-to-default'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(
						document.hrefFm,
						itemData.resetCustomizationViewURL
					);
				}
			},
		});
	},

	toggleCustomizedViewMessage(itemData) {
		submitForm(document.hrefFm, itemData.toggleCustomizedViewMessageURL);
	},
};

export default function propsTransformer({actions, items, ...props}) {
	const updateItem = (item) => {
		const newItem = {
			...item,
			onClick(event) {
				const action = item.data?.action;

				if (action) {
					event.preventDefault();

					ACTIONS[action]?.(item.data);
				}
			},
		};

		if (Array.isArray(item.items)) {
			newItem.items = item.items.map(updateItem);
		}

		return newItem;
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
