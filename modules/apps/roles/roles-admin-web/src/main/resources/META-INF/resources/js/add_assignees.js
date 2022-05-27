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
	createPortletURL,
	openSelectionModal,
	postForm,
	sub,
} from 'frontend-js-web';

export default function addAssignees({
	editRoleAssignmentsURL,
	portletNamespace,
	portletURL,
	roleName,
	selectAssigneesURL,
}) {
	openSelectionModal({
		multiple: true,
		onSelect(selectedItem) {
			if (selectedItem) {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (!form) {
					return;
				}

				const assignmentsRedirect = createPortletURL(portletURL, {
					tabs2: selectedItem.type,
				});

				const data = {
					redirect: assignmentsRedirect.toString(),
				};

				if (selectedItem.type === 'segments') {
					data.addSegmentsEntryIds = selectedItem.value;
				}
				else if (selectedItem.type === 'users') {
					data.addUserIds = selectedItem.value;
				}
				else {
					data.addGroupIds = selectedItem.value;
				}

				postForm(form, {
					data,
					url: editRoleAssignmentsURL,
				});
			}
		},
		selectEventName: `${portletNamespace}selectAssignees`,
		title: sub(Liferay.Language.get('add-assignees-to-x'), roleName),
		url: selectAssigneesURL,
	});
}
