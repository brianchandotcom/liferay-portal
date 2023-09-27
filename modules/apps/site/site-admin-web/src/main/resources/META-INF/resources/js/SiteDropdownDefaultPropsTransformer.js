/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal} from 'frontend-js-web';

import openDeleteStyleBookModal from './openDeleteSiteModal';

const ACTIONS = {
	activateSite(itemData) {
		this.send(itemData.activateSiteURL);
	},

	deactivateSite(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-deactivate-this'
			),
			onConfirm: (isConfirm) => {
				if (isConfirm) {
					this.send(itemData.deactivateSiteURL);
				}
			},
		});
	},

	deleteSite(itemData) {
		openDeleteStyleBookModal({
			onDelete: () => {
				this.send(itemData.deleteSiteURL);
			},
		});
	},

	leaveSite(itemData) {
		this.send(itemData.leaveSiteURL);
	},

	send(url) {
		submitForm(document.hrefFm, url);
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
