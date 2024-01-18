/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {COOKIE_TYPES, sessionStorage} from 'frontend-js-web';

import addAssignees from './addAssignees';

export default function editRoleAssignmentsMain({
	editRoleAssignmentsURL,
	modalSegmentState,
	namespace,
	portletURL,
	roleName,
	selectAssigneesURL,
}) {
	const state = sessionStorage.getItem(
		modalSegmentState,
		COOKIE_TYPES.NECESSARY
	);

	if (state === 'open') {
		sessionStorage.removeItem(modalSegmentState);

		addAssignees({
			editRoleAssignmentsURL,
			modalSegmentState,
			namespace,
			portletURL,
			roleName,
			selectAssigneesURL,
		});
	}
}
