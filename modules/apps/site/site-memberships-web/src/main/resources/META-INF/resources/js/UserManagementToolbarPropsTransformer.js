/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	addParams,
	getPortletId,
	openConfirmModal,
	openSelectionModal,
	sub,
} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const deleteSelectedUsers = () => {
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

	const selectTeams = (itemData) => {
		openSelectionModal({
			onSelect: (selectedItem) => {
				location.href = addParams(
					`${`${portletNamespace}teamId`}=${selectedItem.id}`,
					itemData.viewTeamURL
				);
			},
			selectEventName: `${portletNamespace}selectTeam`,
			title: Liferay.Language.get('select-team'),
			url: itemData?.selectTeamsURL,
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
					input.value = selectedItems.map((selectedItem) => {
						const item = JSON.parse(selectedItem.value);

						return item.id;
					});

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
			else if (item?.data?.action === 'selectTeams') {
				selectTeams(item?.data);
			}
		},
	};
}
