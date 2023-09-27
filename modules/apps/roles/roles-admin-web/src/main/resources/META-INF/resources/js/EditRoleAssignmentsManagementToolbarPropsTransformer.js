/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getCheckedCheckboxes, postForm} from 'frontend-js-web';

import addAssignees from './add_assignees';

export default function propsTransformer({
	additionalProps: {
		assigneeType,
		editRoleAssignmentsURL,
		portletURL,
		roleName,
		selectAssigneesURL,
	},
	portletNamespace,
	...otherProps
}) {
	const unsetRoleAssignments = () => {
		const form = document.getElementById(`${portletNamespace}fm`);

		if (!form) {
			return;
		}

		const ids = getCheckedCheckboxes(form, `${portletNamespace}allRowIds`);

		const data = {
			assignmentsRedirect: portletURL,
		};

		if (assigneeType === 'segments') {
			data.removeSegmentsEntryIds = ids;
		}
		else if (assigneeType === 'users') {
			data.removeUserIds = ids;
		}
		else {
			data.removeGroupIds = ids;
		}

		postForm(form, {
			data,
			url: editRoleAssignmentsURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick() {
			unsetRoleAssignments();
		},
		onCreateButtonClick() {
			addAssignees({
				editRoleAssignmentsURL,
				portletNamespace,
				portletURL,
				roleName,
				selectAssigneesURL,
			});
		},
	};
}
