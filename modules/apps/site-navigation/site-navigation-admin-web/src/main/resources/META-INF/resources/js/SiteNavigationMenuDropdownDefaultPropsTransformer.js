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

import {openModal, openSimpleInputModal} from 'frontend-js-web';

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
		if (
			itemData.confirmationMessage &&
			!confirm(itemData.confirmationMessage)
		) {
			return;
		}
		submitForm(document.hrefFm, itemData.markAsPrimaryURL);
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
