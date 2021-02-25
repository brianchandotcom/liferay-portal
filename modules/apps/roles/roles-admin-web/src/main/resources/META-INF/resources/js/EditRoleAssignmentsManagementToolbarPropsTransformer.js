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

import {postForm} from 'frontend-js-web';

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

		const ids = Liferay.Util.listCheckedExcept(
			form,
			`${portletNamespace}allRowIds`
		);

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
