/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openConfirmModal, openSelectionModal} from 'frontend-js-web';

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
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-remove-this-user'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					submitForm(document.hrefFm, itemData.removeAccountUsersURL);
				}
			},
		});
	},
};
