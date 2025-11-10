/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SelectUserGroupPage} from '../pages/SelectUserGroupPage';
import {SelectUserPage} from '../pages/SelectUserPage';
import {UserGroupsPage} from '../pages/UserGroupsPage';
import {UsersPage} from '../pages/UsersPage';

const siteTeamsPagesTest = test.extend<{
	selectUserGroupPage: SelectUserGroupPage;
	selectUserPage: SelectUserPage;
	userGroupsPage: UserGroupsPage;
	usersPage: UsersPage;
}>({
	selectUserGroupPage: async ({page}, use) => {
		await use(new SelectUserGroupPage(page));
	},
	selectUserPage: async ({page}, use) => {
		await use(new SelectUserPage(page));
	},
	userGroupsPage: async ({page}, use) => {
		await use(new UserGroupsPage(page));
	},
	usersPage: async ({page}, use) => {
		await use(new UsersPage(page));
	},
});

export {siteTeamsPagesTest};
