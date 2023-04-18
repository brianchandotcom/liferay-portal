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
	CHANNEL_GENERAL_MANAGER = 'Channel General Manager',
	CHANNEL_ACCOUNT_MANAGER = 'Channel Account Manager (CAM)',
	CHANNEL_REGIONAL_MARKETING_MANAGER = 'Channel Regional Marketing Manager',
	CHANNEL_GLOBAL_MARKETING_MANAGER = 'Channel Global Marketing Manager',
	CHANNEL_FINANCE_MANAGER = 'Channel Finance Manager (CFM)',
}

export function isLiferayManager(roles: Role[]) {
	const allowedRoles = [
		RoleTypes.CHANNEL_GENERAL_MANAGER,
		RoleTypes.CHANNEL_ACCOUNT_MANAGER,
		RoleTypes.CHANNEL_REGIONAL_MARKETING_MANAGER,
		RoleTypes.CHANNEL_GLOBAL_MARKETING_MANAGER,
		RoleTypes.CHANNEL_FINANCE_MANAGER,
	];

	return Boolean(
		roles?.some((role) => allowedRoles.includes(role.name as RoleTypes))
	);
}
