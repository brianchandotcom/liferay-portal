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
			multiple: true,
			onSelect: (selectedItems) => {
				const editUserGroupRoleFm = document.getElementById(
					`${portletNamespace}editUserGroupRoleFm`
				);

				if (!editUserGroupRoleFm) {
					return;
				}

				const input = document.createElement('input');

				input.name = `${portletNamespace}rowIds`;
				input.value = selectedItems.map((item) => item.value);

				editUserGroupRoleFm.appendChild(input);

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
