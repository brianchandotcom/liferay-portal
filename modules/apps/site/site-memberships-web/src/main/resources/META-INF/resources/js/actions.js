/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openConfirmModal,
	openSelectionModal,
	setFormValues,
} from 'frontend-js-web';

export const ACTIONS = {
	assignRoles(itemData, portletNamespace) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			getSelectedItemsOnly: false,
			multiple: true,
			onSelect: (items) => {
				const editUserGroupRoleFm = document.getElementById(
					`${portletNamespace}editUserGroupRoleFm`
				);

				if (!editUserGroupRoleFm) {
					return;
				}

				const allInput = document.createElement('input');

				allInput.name = `${portletNamespace}availableRowIds`;
				allInput.value = items.map((item) => item.value);

				editUserGroupRoleFm.appendChild(allInput);

				const checkedInput = document.createElement('input');

				checkedInput.name = `${portletNamespace}rowIds`;
				checkedInput.value = items
					.filter((item) => item.checked)
					.map((item) => item.value);

				editUserGroupRoleFm.appendChild(checkedInput);

				submitForm(editUserGroupRoleFm, itemData.editUserGroupRoleURL);
			},
			title: Liferay.Language.get('assign-roles'),
			url: itemData.assignRolesURL,
		});
	},

	deleteGroupUsers(itemData) {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
				}
			},
		});
	},

	unassignRoles(itemData, portletNamespace) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				const unassignUserGroupRoleFm = document.getElementById(
					`${portletNamespace}unassignUserGroupRoleFm`
				);

				setFormValues(unassignUserGroupRoleFm, {
					userId: itemData.userId,
				});

				const input = document.createElement('input');

				input.name = `${portletNamespace}rowIds`;

				const selectedUserGroupIds = Array.prototype.map.call(
					selectedItems,
					(item) => item.value
				);

				input.value = selectedUserGroupIds.join();

				unassignUserGroupRoleFm.appendChild(input);

				submitForm(unassignUserGroupRoleFm);
			},
			title: Liferay.Language.get('unassign-roles'),
			url: itemData.unassignUserGroupRoleURL,
		});
	},
};
