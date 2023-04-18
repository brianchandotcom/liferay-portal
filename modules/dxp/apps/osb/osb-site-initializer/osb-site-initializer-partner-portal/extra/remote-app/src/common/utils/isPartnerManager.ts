/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Role from '../interfaces/role';

export enum RoleTypes {
	PARTNER_MANAGER = 'Partner Manager (PM)',
}

export function isPartnerManager(roles: Role[]) {
	const allowedPartnerRole = [RoleTypes.PARTNER_MANAGER];

	return roles.some((role) =>
		allowedPartnerRole.includes(role.name as RoleTypes)
	);
}
