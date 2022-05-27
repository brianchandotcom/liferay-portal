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
			onSelect(selectedItem) {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form && selectedItem) {
					postForm(form, {
						data: {
							addUserIds: selectedItem,
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
