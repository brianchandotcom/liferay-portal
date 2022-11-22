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
