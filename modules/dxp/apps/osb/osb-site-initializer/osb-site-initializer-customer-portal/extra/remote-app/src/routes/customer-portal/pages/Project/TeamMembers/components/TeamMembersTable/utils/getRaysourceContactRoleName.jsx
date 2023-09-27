/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const RAYSOURCE_ROLE_NAMES = {
	Administrator: 'Support Administrator',
	Requester: 'Support Requester',
	User: 'Support User',
};

export default function getRaysourceContactRoleName(roleName) {
	const raySourceRoleName = RAYSOURCE_ROLE_NAMES[roleName] || roleName;

	return `contactRoleNames=${raySourceRoleName}`;
}
