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

import {createPortletURL, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps: {
		assignmentsURL,
		editOrganizationAssignmentURL,
		selectUsersURL,
	},
	portletNamespace,
	...otherProps
}) {
	const selectUsers = (organizationId) => {
		if (!organizationId) {
			return;
		}

		const url = createPortletURL(selectUsersURL, {
			organizationId,
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (data) => {
				if (data) {
					const assignmentsRedirectURL = createPortletURL(
						assignmentsURL,
						{
							organizationId,
						}
					);

					const editAssignmentParameters = {
						addUserIds: data.value,
						assignmentsRedirect: assignmentsRedirectURL.toString(),
						organizationId,
					};

					const editAssignmentURL = createPortletURL(
						editOrganizationAssignmentURL,
						editAssignmentParameters
					);

					const form = document.getElementById(
						`${portletNamespace}fm`
					);

					if (!form) {
						return;
					}

					submitForm(form, editAssignmentURL.toString());
				}
			},
			selectEventName: `${portletNamespace}selectUsers`,
			title: Liferay.Language.get('assign-users'),
			url: url.toString(),
		});
	};

	return {
		...otherProps,
		onCreationMenuItemClick(event, {item}) {
			if (item.data?.action === 'selectUsers') {
				selectUsers(item.data?.organizationId);
			}
		},
	};
}
