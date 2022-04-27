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

import {UserAccount} from '../graphql/queries';
import {Action, Actions, Entity, PermissionCheck, Roles} from '../types';
import {rolePermissions} from './RolePermission';

export class Security {
	public ready: boolean = false;
	public cache: Map<string, boolean> = new Map();
	private userAccount: UserAccount;

	constructor(userAccount: UserAccount) {
		this.userAccount = userAccount;
		this.ready = !!userAccount.id;
	}

	checker(entity: Entity, permission: Actions): boolean {
		for (const role of this.userAccount.roleBriefs) {
			const key = `${entity}/${permission}`;
			const cachedValue = this.cache.get(key);

			if (cachedValue) {
				return cachedValue;
			}

			const userRole = role.name.replace(' ', '_').toUpperCase() as Roles;

			const rolePermission =
				rolePermissions[userRole] || rolePermissions.TESTRAY_USER;

			const hasPermission = rolePermission[entity]?.includes(permission);

			if (hasPermission) {
				this.cache.set(key, true);

				return true;
			}
		}

		return false;
	}

	filterActions(actions: Action[], entity: Entity): Action[] {
		return actions.filter((action) =>
			typeof action.permission === 'boolean'
				? action.permission
				: this.checker(entity, action.permission as Actions)
		);
	}

	permissions(entity: Entity, permissions: Actions[]): PermissionCheck {
		const permissionCheck: PermissionCheck = {};

		for (const permission of permissions) {
			permissionCheck[permission] = this.checker(entity, permission);
		}

		return permissionCheck;
	}
}
