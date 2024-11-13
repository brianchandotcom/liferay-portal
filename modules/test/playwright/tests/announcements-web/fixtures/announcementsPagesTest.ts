/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AnnouncementsPage} from '../pages/AnnouncementsPage';
import {AnnouncementsWidgetConfigurationPage} from '../pages/AnnouncementsWidgetConfigurationPage';
import {AnnouncementsWidgetPage} from '../pages/AnnouncementsWidgetPage';

const announcementsPagesTest = test.extend<{
	announcementsPage: AnnouncementsPage;
	announcementsWidgetConfigurationPage: AnnouncementsWidgetConfigurationPage;
	announcementsWidgetPage: AnnouncementsWidgetPage;
}>({
	announcementsPage: async ({page}, use) => {
		await use(new AnnouncementsPage(page));
	},
	announcementsWidgetConfigurationPage: async ({page}, use) => {
		await use(new AnnouncementsWidgetConfigurationPage(page));
	},
	announcementsWidgetPage: async ({page}, use) => {
		await use(new AnnouncementsWidgetPage(page));
	},
});

export {announcementsPagesTest};
