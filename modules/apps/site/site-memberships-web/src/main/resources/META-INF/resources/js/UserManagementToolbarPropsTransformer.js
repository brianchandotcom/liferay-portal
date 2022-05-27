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
	addParams,
	getPortletId,
	openSelectionModal,
	sub,
} from 'frontend-js-web';

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
			onSelect: (selectedItems) => {
				if (selectedItems.length) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					const input = document.createElement('input');

					input.name = `${portletNamespace}rowIdsRole`;
					input.value = selectedItems.map((item) => item.value);

					form.appendChild(input);

					submitForm(form, itemData?.editUsersRolesURL);
				}
			},
			title: Liferay.Language.get('assign-roles'),
			url: itemData?.selectRoleURL,
		});
	};

	const selectRoles = (itemData) => {
		openSelectionModal({
			onSelect: (selectedItem) => {
				location.href = addParams(
					`${`${portletNamespace}roleId`}=${selectedItem.id}`,
					itemData.viewRoleURL
				);
			},
			selectEventName: `${portletNamespace}selectRole`,
			title: Liferay.Language.get('select-role'),
			url: itemData?.selectRolesURL,
		});
	};

	const selectUsers = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems.length) {
					const addGroupUsersFm = document.getElementById(
						`${portletNamespace}addGroupUsersFm`
					);

					if (!addGroupUsersFm) {
						return;
					}

					const input = document.createElement('input');

					input.name = `${portletNamespace}rowIds`;
					input.value = selectedItems.map((item) => item.value);

					addGroupUsersFm.appendChild(input);

					submitForm(addGroupUsersFm);
				}
			},
			title: sub(
				Liferay.Language.get('assign-users-to-this-x'),
				itemData?.groupTypeLabel
			),
			url: addParams(
				`p_p_id=${getPortletId(portletNamespace)}`,
				itemData?.selectUsersURL
			),
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedUsers') {
				deleteSelectedUsers();
			}
			else if (action === 'selectRole') {
				selectRole(data);
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			if (data?.action === 'selectUsers') {
				selectUsers(data);
			}
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'selectRoles') {
				selectRoles(item?.data);
			}
		},
	};
}
