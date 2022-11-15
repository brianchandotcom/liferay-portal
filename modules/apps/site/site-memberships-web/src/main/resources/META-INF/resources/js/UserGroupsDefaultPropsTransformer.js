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

import {
	openConfirmModal,
	openSelectionModal,
	setFormValues,
} from 'frontend-js-web';

const ACTIONS = {
	assignUserGroupRole(itemData, portletNamespace) {
		const addUserGroupGroupRoleFm =
			document[`${portletNamespace}addUserGroupGroupRoleFm`];

		if (!addUserGroupGroupRoleFm) {
			return;
		}

		setFormValues(addUserGroupGroupRoleFm, {
			userGroupId: itemData.userGroupId,
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				if (!selectedItems) {
					return;
				}

				const input = document.createElement('input');

				input.name = `${portletNamespace}rowIds`;

				const selectedUserGroupIds = Array.prototype.map.call(
					selectedItems,
					(item) => item.value
				);

				input.value = selectedUserGroupIds.join();

				addUserGroupGroupRoleFm.appendChild(input);

				submitForm(addUserGroupGroupRoleFm);
			},
			title: Liferay.Language.get('assign-roles'),
			url: itemData.assignUserGroupRoleURL,
		});
	},

	deleteGroupUserGroups(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) =>
				isConfirmed &&
				submitForm(document.hrefFm, itemData.deleteGroupUserGroupsURL),
		});
	},

	unassignUserGroupRole(itemData, portletNamespace) {
		const unassignUserGroupGroupRoleFm =
			document[`${portletNamespace}unassignUserGroupGroupRoleFm`];

		if (!unassignUserGroupGroupRoleFm) {
			return;
		}

		setFormValues(unassignUserGroupGroupRoleFm, {
			userGroupId: itemData.userGroupId,
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				if (!selectedItems) {
					return;
				}

				const input = document.createElement('input');

				input.name = `${portletNamespace}rowIds`;

				const selectedUserGroupIds = Array.prototype.map.call(
					selectedItems,
					(item) => item.value
				);

				input.value = selectedUserGroupIds.join();

				unassignUserGroupGroupRoleFm.appendChild(input);

				submitForm(unassignUserGroupGroupRoleFm);
			},
			title: Liferay.Language.get('unassign-roles'),
			url: itemData.unassignUserGroupRoleURL,
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
