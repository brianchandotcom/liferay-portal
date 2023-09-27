/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
