/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {UsersAndOrganizationsPage} from '../pages/users-admin-web/usersAndOrganizations.page';

const usersAndOrganizationsPagesTest = test.extend<{
	_usersAndOrganizationsPage: UsersAndOrganizationsPage;
}>({
	_usersAndOrganizationsPage: async ({page}, use) => {
		await use(new UsersAndOrganizationsPage(page));
	},
});

export {usersAndOrganizationsPagesTest};
