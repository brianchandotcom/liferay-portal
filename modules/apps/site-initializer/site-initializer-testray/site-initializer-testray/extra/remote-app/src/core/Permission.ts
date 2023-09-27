/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Action} from '../types';

type PermissionObj = {
	href: string;
	method: string;
};

type ObjectPermission = {
	delete?: PermissionObj;
	get?: PermissionObj;
	update?: PermissionObj;
};

export default class Permission {
	static filterActions(
		actions: Action[],
		permissions: ObjectPermission = {}
	) {
		return actions.filter((action) =>
			typeof action.permission === 'boolean'
				? action.permission
				: action.permission
				? !!(permissions as any)[action?.permission?.toLowerCase()]
				: true
		);
	}
}
