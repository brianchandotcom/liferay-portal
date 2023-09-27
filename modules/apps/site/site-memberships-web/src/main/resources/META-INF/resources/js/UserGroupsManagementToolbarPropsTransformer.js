/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	openConfirmModal,
	openSelectionModal,
	sub,
} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedUserGroups = () => {
		openConfirmModal({
			message: Liferay.Language.get(
				'are-you-sure-you-want-to-delete-this'
			),
			onConfirm: (isConfirmed) => {
				if (isConfirmed) {
					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (form) {
						submitForm(form);
					}
				}
			},
		});
	};

	const selectRole = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				if (selectedItems) {
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

					submitForm(form, itemData?.editUserGroupsRolesURL);
				}
			},
			selectEventName: `${portletNamespace}selectRole`,
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

	const selectUserGroups = (itemData) => {
		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect(selectedItems) {
				if (selectedItems.length) {
					const addGroupUserGroupsFm = document.getElementById(
						`${portletNamespace}addGroupUserGroupsFm`
					);

					if (!addGroupUserGroupsFm) {
						return;
					}

					const input = document.createElement('input');

					input.name = `${portletNamespace}rowIds`;
					input.value = selectedItems.map((selectedItem) => {
						const item = JSON.parse(selectedItem.value);

						return item.userGroupId;
					});

					addGroupUserGroupsFm.appendChild(input);

					submitForm(addGroupUserGroupsFm);
				}
			},
			selectEventName: `${portletNamespace}selectUserGroups`,
			title: sub(
				Liferay.Language.get('assign-user-groups-to-this-x'),
				itemData?.groupTypeLabel
			),
			url: itemData?.selectUserGroupsURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'deleteSelectedUserGroups') {
				deleteSelectedUserGroups();
			}
			else if (action === 'selectRole') {
				selectRole(data);
			}
		},
		onCreateButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'selectUserGroups') {
				selectUserGroups(data);
			}
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'selectRoles') {
				selectRoles(item?.data);
			}
		},
	};
}
