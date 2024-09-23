/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {HomePage} from '../pages/HomePage';
import {ProjectAttachmentsPage} from '../pages/ProjectAttachmentsPage';
import {ProjectOverviewPage} from '../pages/ProjectOverviewPage';
import {ProjectTeamMembersPage} from '../pages/ProjectTeamMembersPage';
import {ProjectPaaSPage} from '../pages/projectPaaSPage';

export const customerPagesTest = test.extend<{
	homePage: HomePage;
	projectAttachmentsPage: ProjectAttachmentsPage;
	projectOverviewPage: ProjectOverviewPage;
	projectPaaSPage: ProjectPaaSPage;
	projectTeamMembersPage: ProjectTeamMembersPage;
}>({
	homePage: async ({page}, use) => {
		await use(new HomePage(page));
	},
	projectAttachmentsPage: async ({page}, use) => {
		await use(new ProjectAttachmentsPage(page));
	},
	projectOverviewPage: async ({page}, use) => {
		await use(new ProjectOverviewPage(page));
	},
	projectPaaSPage: async ({page}, use) => {
		await use(new ProjectPaaSPage(page));
	},
	projectTeamMembersPage: async ({page}, use) => {
		await use(new ProjectTeamMembersPage(page));
	},
});
