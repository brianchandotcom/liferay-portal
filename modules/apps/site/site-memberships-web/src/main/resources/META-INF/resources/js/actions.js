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
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
		}
	},
};
