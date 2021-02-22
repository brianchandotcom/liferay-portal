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

import {addParams, getPortletId, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedUsers = () => {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form);
			}
		}
	};

	const selectRole = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItem) {
				if (selectedItem) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					selectedItem.forEach((item) => {
						form.appendChild(item);
					});

					submitForm(form, itemData.editUsersRolesURL);
				}
			},
			selectEventName: `${portletNamespace}selectRole`,
			title: Liferay.Language.get('assign-roles'),
			url: itemData.selectRoleURL,
		});
	};

	const selectUsers = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItem) {
				if (selectedItem) {
					const form = document.getElementById(
						`${portletNamespace}addGroupUsersFm`
					);

					if (!form) {
						return;
					}

					selectedItem.forEach((item) => {
						form.appendChild(item);
					});

					submitForm(form);
				}
			},
			selectEventName: `${portletNamespace}selectUsers`,
			title: Liferay.Util.sub(
				Liferay.Language.get('assign-users-to-this-x'),
				itemData.groupTypeLabel
			),
			url: addParams(
				`p_p_id=${getPortletId(portletNamespace)}`,
				itemData.selectUsersURL
			),
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'deleteSelectedUsers') {
				deleteSelectedUsers();
			}
			else if (action === 'selectRole') {
				selectRole(item.data);
			}
		},
		onCreateButtonClick(event, {item}) {
			const action = item.data?.action;

			if (action === 'selectUsers') {
				selectUsers(item.data);
			}
		},
	};
}
