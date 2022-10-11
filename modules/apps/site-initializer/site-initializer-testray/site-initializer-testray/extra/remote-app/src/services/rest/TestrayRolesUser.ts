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

import yupSchema from '../../schema/yup';
import Rest from './Rest';
import {Role, UserAccount} from './types';

type RoleForm = typeof yupSchema.role.__outputType;

class LiferayUserRolesRest extends Rest<RoleForm, Role> {
	constructor() {
		super({
			uri: 'roles',
		});
	}

	public async rolesToUser(
		roles: number[],
		roleBriefs: Role[] = [],
		userAccount: UserAccount
	): Promise<UserAccount> {
		const deleteRoles = roleBriefs
			.filter(
				(roleBrief) =>
					!roles.find((role) => Number(role) === Number(roleBrief.id))
			)
			.map((roleBrief) => roleBrief.id as number);

		const addRoles = roles.filter(
			(rolesItems) =>
				!roleBriefs.find(
					(item) => Number(item.id) === Number(rolesItems)
				)
		);

		for (const rolesUserDelete of deleteRoles) {
			await this.fetcher.delete(
				`/roles/${rolesUserDelete}/association/user-account/${userAccount.id}`
			);
		}

		for (const rolesUserUpdate of addRoles) {
			await this.fetcher.post(
				`/roles/${rolesUserUpdate}/association/user-account/${userAccount.id}`
			);
		}

		userAccount.roleBriefs = [
			...userAccount.roleBriefs.filter(
				(roleBrief) => !deleteRoles.includes(roleBrief.id)
			),
			...addRoles.map((roleId) => ({id: roleId, name: 'www'})),
		];

		return userAccount;
	}
}

const liferayUserRolesRest = new LiferayUserRolesRest();

export {liferayUserRolesRest};
