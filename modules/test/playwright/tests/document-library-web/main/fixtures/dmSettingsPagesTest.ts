/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FileSizeLimitsInstanceSettingsPage} from '../pages/FileSizeLimitsInstanceSettingsPage';
import {FileSizeLimitsSiteSettingsPage} from '../pages/FileSizeLimitsSiteSettingsPage';
import {FileSizeLimitsSystemSettingsPage} from '../pages/FileSizeLimitsSystemSettingsPage';

const dmSettingsPagesTest = test.extend<{
	fileSizeLimitsInstanceSettingsPage: FileSizeLimitsInstanceSettingsPage;
	fileSizeLimitsSiteSettingsPage: FileSizeLimitsSiteSettingsPage;
	fileSizeLimitsSystemSettingsPage: FileSizeLimitsSystemSettingsPage;
}>({
	fileSizeLimitsInstanceSettingsPage: async ({page}, use) => {
		await use(new FileSizeLimitsInstanceSettingsPage(page));
	},
	fileSizeLimitsSiteSettingsPage: async ({page}, use) => {
		await use(new FileSizeLimitsSiteSettingsPage(page));
	},
	fileSizeLimitsSystemSettingsPage: async ({page}, use) => {
		await use(new FileSizeLimitsSystemSettingsPage(page));
	},
});

export {dmSettingsPagesTest};
