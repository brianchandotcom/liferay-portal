/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getCheckedCheckboxes,
	openSelectionModal,
	postForm,
	sub,
} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		editUserGroupAssignmentsURL,
		portletURL,
		selectUsersURL,
		userGroupName,
	},
	portletNamespace,
	...otherProps
}) {
	const addUsers = () => {
		openSelectionModal({
			multiple: true,
			onSelect(data) {
				const selectedItems = data.value;

				const form = document.getElementById(`${portletNamespace}fm`);

				if (form && selectedItems.length) {
					postForm(form, {
						data: {
							addUserIds: selectedItems.map((selectedItem) => {
								const item = JSON.parse(selectedItem);

								return item.id;
							}),
						},
						url: editUserGroupAssignmentsURL,
					});
				}
			},
			selectEventName: `${portletNamespace}selectUsers`,
			title: sub(Liferay.Language.get('add-users-to-x'), userGroupName),
			url: selectUsersURL,
		});
	};

	const removeUsers = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (form) {
			postForm(form, {
				data: {
					redirect: portletURL,
					removeUserIds: getCheckedCheckboxes(
						form,
						`${portletNamespace}allRowIds`
					),
				},
				url: editUserGroupAssignmentsURL,
			});
		}
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			if (item?.data?.action === 'removeUsers') {
				removeUsers();
			}
		},
		onCreateButtonClick(event, {item}) {
			if (item?.data?.action === 'addUsers') {
				addUsers();
			}
		},
	};
}
