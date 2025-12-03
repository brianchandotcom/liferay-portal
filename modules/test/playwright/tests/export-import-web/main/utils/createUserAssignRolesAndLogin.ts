/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {performUserSwitch, userData} from '../../../../utils/performLogin';

import type {Page} from '@playwright/test';

import type {DataApiHelpers} from '../../../../helpers/ApiHelpers';

export async function createUserAssignRolesAndLogin({
	apiHelpers,
	page,
	roleNames = ['Administrator', 'Power User'],
}: {
	apiHelpers: DataApiHelpers;
	page: Page;
	roleNames?: string[];
}): Promise<TUserAccount> {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();
	apiHelpers.data.push({id: user.id, type: 'account'});

	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};

	await Promise.all(
		roleNames.map(async (roleName) => {
			const roles = await apiHelpers.headlessAdminUser.getRoles(roleName);
			apiHelpers.headlessAdminUser.postRoleUserAccountAssociation(
				roles.items[0].id,
				Number(user.id)
			);
		})
	);

	await performUserSwitch(page, user.alternateName);

	return user;
}
