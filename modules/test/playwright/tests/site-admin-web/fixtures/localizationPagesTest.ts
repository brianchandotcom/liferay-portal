/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {LocalizationInstanceSettingsPage} from '../pages/LocalizationInstanceSettingsPage';

const localizationPagesTest = test.extend<{
	localizationInstanceSettingsPage: LocalizationInstanceSettingsPage;
}>({
	localizationInstanceSettingsPage: async ({page}, use) => {
		await use(new LocalizationInstanceSettingsPage(page));
	},
});

export {localizationPagesTest};
