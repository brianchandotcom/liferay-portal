/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {RoleDefinePermissionsPage} from '../pages/roles-admin-web/RoleDefinePermissionsPage';
import {RolePage} from '../pages/roles-admin-web/RolePage';
import {RolesPage} from '../pages/roles-admin-web/RolesPage';

const rolesPagesTest = test.extend<{
	roleDefinePermissionsPage: RoleDefinePermissionsPage;
	rolePage: RolePage;
	rolesPage: RolesPage;
}>({
	roleDefinePermissionsPage: async ({page}, use) => {
		await use(new RoleDefinePermissionsPage(page));
	},
	rolePage: async ({page}, use) => {
		await use(new RolePage(page));
	},
	rolesPage: async ({page}, use) => {
		await use(new RolesPage(page));
	},
});

export {rolesPagesTest};
