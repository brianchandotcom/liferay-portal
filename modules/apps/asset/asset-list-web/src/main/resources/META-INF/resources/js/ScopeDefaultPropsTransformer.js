/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

const ACTIONS = {
	addRow({groupId}, searchContainer, portletNamespace) {
		const data = searchContainer.getData(true);

		if (data.includes(groupId)) {
			return;
		}

		searchContainer.addRow([], groupId);

		const groupIds = document.getElementById(`${portletNamespace}groupIds`);

		if (groupIds) {
			const searchContainerData = searchContainer.getData();

			groupIds.setAttribute('value', searchContainerData.split(','));

			submitForm(document[`${portletNamespace}fm`]);
		}
	},

	selectManageableGroup(
		{groupItemSelectorURL, selectEventName},
		searchContainer,
		portletNamespace
	) {
		openSelectionModal({
			id: selectEventName,
			onSelect(selectedItem) {
				const entityId = selectedItem.groupid;

				const searchContainerData = searchContainer.getData();

				if (searchContainerData.indexOf(entityId) === -1) {
					ACTIONS.addRow(
						{groupId: entityId},
						searchContainer,
						portletNamespace
					);
				}
			},
			selectEventName,
			title: Liferay.Language.get('scope'),
			url: groupItemSelectorURL,
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

					const searchContainer = Liferay.SearchContainer.get(
						`${portletNamespace}groupsSearchContainer`
					);

					ACTIONS[action]?.(
						item.data,
						searchContainer,
						portletNamespace
					);
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
