/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createPortletURL,
	fetch,
	objectToFormData,
	openModal,
	openSelectionModal,
} from 'frontend-js-web';

export default function decorateAddSiteNavigationMenuItemOptions({
	addSiteNavigationMenuItemOptions,
	portletNamespace,
}) {
	const onItemAdd = () => {
		window.sessionStorage.setItem(`${portletNamespace}itemAdded`, true);
	};

	const onClick = ({
		itemData: data,
		order,
		parentSiteNavigationMenuItemId,
	}) => {
		const useSmallerModal = shouldUseSmallerModal(data.type);

		if (data.itemSelector) {
			openSelectionModal({
				buttonAddLabel: data.multiSelection
					? Liferay.Language.get('select')
					: null,
				height: useSmallerModal ? '60vh' : undefined,
				multiple: data.multiSelection,

				onSelect: (selection) => {
					const addItemURL = createPortletURL(data.addItemURL, {
						order,
						parentSiteNavigationMenuItemId,
					});

					fetch(addItemURL, {
						body: objectToFormData(
							data.multiSelection
								? getNamespacedInfoItems(
										portletNamespace,
										selection,
										data.siteNavigationMenuId
								  )
								: getNamespacedInfoItem(
										portletNamespace,
										selection,
										data.siteNavigationMenuId
								  )
						),
						method: 'POST',
					}).then(() => {
						onItemAdd();

						window.location.reload();
					});
				},

				selectEventName: `${portletNamespace}selectItem`,
				size: useSmallerModal ? 'md' : undefined,
				title: data.addTitle,
				url: data.href,
			});
		}
		else {
			const url = createPortletURL(data.href, {
				order,
				parentSiteNavigationMenuItemId,
			});

			openModal({
				height: useSmallerModal ? '60vh' : undefined,
				id: `${portletNamespace}addMenuItem`,
				iframeBodyCssClass: 'portal-popup',
				size: useSmallerModal ? 'md' : undefined,
				title: data.addTitle,
				url,
			});

			Liferay.once('reloadSiteNavigationMenuEditor', () => {
				onItemAdd();

				window.location.reload();
			});
		}
	};

	return addSiteNavigationMenuItemOptions.map((item) => ({
		...item,
		onClick: ({order, parentSiteNavigationMenuItemId}) =>
			onClick({
				itemData: item.data,
				order,
				parentSiteNavigationMenuItemId,
			}),
	}));
}

function getNamespacedInfoItem(
	portletNamespace,
	selectedItem,
	siteNavigationMenuId
) {
	if (!selectedItem) {
		return;
	}

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
	else if (selectedItem.value && typeof selectedItem.value === 'object') {
		value = selectedItem.value;
	}

	if (value) {
		delete infoItem.value;
		infoItem = {...value};
	}

	infoItem.siteNavigationMenuId = siteNavigationMenuId;

	return Liferay.Util.ns(portletNamespace, infoItem);
}

function getNamespacedInfoItems(
	portletNamespace,
	selectedItems,
	siteNavigationMenuId
) {
	if (!selectedItems) {
		return;
	}

	let selectedItemsValue = selectedItems;

	if (selectedItems.value && Array.isArray(selectedItems.value)) {
		selectedItemsValue = selectedItems.value.map((item) =>
			JSON.parse(item)
		);
	}
	else if (typeof selectedItems === 'object') {
		selectedItemsValue = Object.values(selectedItems);
	}

	if (!selectedItemsValue.length) {
		return;
	}

	const infoItems = {
		items: JSON.stringify(selectedItemsValue),
		siteNavigationMenuId,
	};

	return Liferay.Util.ns(portletNamespace, infoItems);
}

const SMALLER_MODAL_TYPES = [
	'com.liferay.asset.kernel.model.AssetCategory',
	'layout',
	'node',
	'url',
];

function shouldUseSmallerModal(type) {
	return SMALLER_MODAL_TYPES.includes(type);
}
