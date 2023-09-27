/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openConfirmModal,
	openModal,
	openSimpleInputModal,
} from 'frontend-js-web';

import openDeleteSiteNavigationMenuModal from './openDeleteSiteNavigationMenuModal';

const ACTIONS = {
	deleteSiteNavigationMenu(itemData) {
		openDeleteSiteNavigationMenuModal({
			onDelete: () => {
				submitForm(
					document.hrefFm,
					itemData.deleteSiteNavigationMenuURL
				);
			},
		});
	},

	markAsPrimary(itemData) {
		if (itemData.confirmationMessage) {
			openConfirmModal({
				message: itemData.confirmationMessage,
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						submitForm(document.hrefFm, itemData.markAsPrimaryURL);
					}
				},
			});
		}
		else {
			submitForm(document.hrefFm, itemData.markAsPrimaryURL);
		}
	},

	markAsSecondary(itemData) {
		submitForm(document.hrefFm, itemData.markAsSecondaryURL);
	},

	markAsSocial(itemData) {
		submitForm(document.hrefFm, itemData.markAsSocialURL);
	},

	permissionsSiteNavigationMenu(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsSiteNavigationMenuURL,
		});
	},

	renameSiteNavigationMenu(itemData, namespace) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-site-navigation-menu'),
			formSubmitURL: itemData.renameSiteNavigationMenuURL,
			idFieldName: 'id',
			idFieldValue: itemData.idFieldValue,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.mainFieldValue,
			namespace,
		});
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
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
