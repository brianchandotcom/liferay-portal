/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SiteSettingsLocalizationPage} from '../pages/site-admin-web/SiteSettingsLocalizationPage';

const siteSettingsPageTests = test.extend<{
	siteSettingsLocalizationPage: SiteSettingsLocalizationPage;
}>({
	siteSettingsLocalizationPage: async ({page}, use) => {
		await use(new SiteSettingsLocalizationPage(page));
	},
});

export {siteSettingsPageTests};
