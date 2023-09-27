/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
