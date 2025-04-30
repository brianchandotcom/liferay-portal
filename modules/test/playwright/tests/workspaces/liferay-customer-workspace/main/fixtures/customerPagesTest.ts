/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {HomePage} from '../pages/HomePage';
import {LoginPage} from '../pages/LoginPage';
import {LogoutPage} from '../pages/LogoutPage';
import {ProjectAttachmentsPage} from '../pages/ProjectAttachmentsPage';
import {ProjectLiferayPaaSPage} from '../pages/ProjectLiferayPaaSPage';
import {ProjectOverviewPage} from '../pages/ProjectOverviewPage';
import {ProjectTeamMembersPage} from '../pages/ProjectTeamMembersPage';

export const customerPagesTest = test.extend<{
	homePage: HomePage;
	loginPage: LoginPage;
	logoutPage: LogoutPage;
	projectAttachmentsPage: ProjectAttachmentsPage;
	projectLiferayPaaSPage: ProjectLiferayPaaSPage;
	projectOverviewPage: ProjectOverviewPage;
	projectTeamMembersPage: ProjectTeamMembersPage;
}>({
	homePage: async ({page}, use) => {
		await use(new HomePage(page));
	},
	loginPage: async ({page}, use) => {
		await use(new LoginPage(page));
	},
	logoutPage: async ({page}, use) => {
		await use(new LogoutPage(page));
	},
	projectAttachmentsPage: async ({page}, use) => {
		await use(new ProjectAttachmentsPage(page));
	},
	projectLiferayPaaSPage: async ({page}, use) => {
		await use(new ProjectLiferayPaaSPage(page));
	},
	projectOverviewPage: async ({page}, use) => {
		await use(new ProjectOverviewPage(page));
	},
	projectTeamMembersPage: async ({page}, use) => {
		await use(new ProjectTeamMembersPage(page));
	},
});
