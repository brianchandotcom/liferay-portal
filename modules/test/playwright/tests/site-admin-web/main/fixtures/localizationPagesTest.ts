/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {LocalizationInstanceSettingsPage} from '../pages/LocalizationInstanceSettingsPage';

const localizationPagesTest = test.extend<{
	localizationInstanceSettingsPage: LocalizationInstanceSettingsPage;
	restoreInstanceDefaultLanguage: void;
}>({
	localizationInstanceSettingsPage: async ({page}, use) => {
		await use(new LocalizationInstanceSettingsPage(page));
	},
	restoreInstanceDefaultLanguage: async (
		{localizationInstanceSettingsPage},
		use
	) => {
		try {
			await use();
		}
		finally {
			await localizationInstanceSettingsPage.goto('Language', false);
			await localizationInstanceSettingsPage.setDefaultLanguage('en_US');
		}
	},
});

export {localizationPagesTest};
