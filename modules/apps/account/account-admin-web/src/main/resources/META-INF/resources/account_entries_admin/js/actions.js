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
	assignRoleAccountUsers(itemData, portletNamespace) {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			containerProps: {
				className: '',
			},
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: (selectedItems) => {
				if (Array.isArray(selectedItems)) {
					const assignRoleAccountUsersFm = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!assignRoleAccountUsersFm) {
						return;
					}

					const input = document.createElement('input');

					input.name = `${portletNamespace}accountRoleIds`;
					input.value = selectedItems.map((item) => item.value);

					assignRoleAccountUsersFm.appendChild(input);

					submitForm(
						assignRoleAccountUsersFm,
						itemData.editRoleAccountUsersURL
					);
				}
			},
			title: Liferay.Language.get('assign-roles'),
			url: itemData.assignRoleAccountUsersURL,
		});
	},

	removeAccountUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-remove-this-user'
				)
			)
		) {
			submitForm(document.hrefFm, itemData.removeAccountUsersURL);
		}
	},
};
