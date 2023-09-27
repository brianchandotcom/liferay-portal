/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openSimpleInputModal} from 'frontend-js-web';

import openDeleteAssetEntryListModal from './openDeleteAssetEntryListModal';

const ACTIONS = {
	deleteAssetListEntry(itemData) {
		openDeleteAssetEntryListModal({
			onDelete: () => {
				this.send(itemData.deleteAssetListEntryURL);
			},
		});
	},

	permissionsAssetEntryList(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsAssetEntryListURL,
		});
	},

	renameAssetListEntry(itemData, portletNamespace) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-collection'),
			formSubmitURL: itemData.renameAssetListEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.assetListEntryId,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			mainFieldPlaceholder: Liferay.Language.get('title'),
			mainFieldValue: itemData.assetListEntryTitle,
			namespace: portletNamespace,
		});
	},

	send(url) {
		submitForm(document.hrefFm, url);
	},
};

export default function propsTransformer({
	actions,
	items,
	portletNamespace,
	...props
}) {
	const updateItem = (item) => {
		return {
			...item,
			items: item.items.map((child) => ({
				...child,
				onClick(event) {
					const action = child.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](child.data, portletNamespace);
					}
				},
			})),
		};
	};

	return {
		...props,
		actions: actions?.map(updateItem),
		items: items?.map(updateItem),
	};
}
