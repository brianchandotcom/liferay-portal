/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ExportUserDataPage} from '../pages/user-associated-data-web/ExportUserDataPage';
import {PersonalDataErasurePage} from '../pages/user-associated-data-web/PersonalDataErasurePage';
import {EditOrganizationPage} from '../pages/users-admin-web/EditOrganizationPage';
import {EditUserPage} from '../pages/users-admin-web/EditUserPage';
import {ServiceAccountsPage} from '../pages/users-admin-web/ServiceAccountsPage';
import {UserPersonalSitePage} from '../pages/users-admin-web/UserPersonalSitePage';
import {UsersAndOrganizationsPage} from '../pages/users-admin-web/UsersAndOrganizationsPage';

const usersAndOrganizationsPagesTest = test.extend<{
	editOrganizationPage: EditOrganizationPage;
	editUserPage: EditUserPage;
	exportUserDataPage: ExportUserDataPage;
	personalDataErasurePage: PersonalDataErasurePage;
	serviceAccountsPage: ServiceAccountsPage;
	userPersonalSitePage: UserPersonalSitePage;
	usersAndOrganizationsPage: UsersAndOrganizationsPage;
}>({
	editOrganizationPage: async ({page}, use) => {
		await use(new EditOrganizationPage(page));
	},
	editUserPage: async ({page}, use) => {
		await use(new EditUserPage(page));
	},
	exportUserDataPage: async ({page}, use) => {
		await use(new ExportUserDataPage(page));
	},
	personalDataErasurePage: async ({page}, use) => {
		await use(new PersonalDataErasurePage(page));
	},
	serviceAccountsPage: async ({page}, use) => {
		await use(new ServiceAccountsPage(page));
	},
	userPersonalSitePage: async ({page}, use) => {
		await use(new UserPersonalSitePage(page));
	},
	usersAndOrganizationsPage: async ({page}, use) => {
		await use(new UsersAndOrganizationsPage(page));
	},
});

export {usersAndOrganizationsPagesTest};
