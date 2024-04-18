/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {LoginInstanceSettingsPage} from '../../../pages/login-web/LoginInstanceSettingsPage';
import {UtilityPagesPage} from '../pages/UtilityPagesPage';

const utilityPagesPage = test.extend<{
	loginInstanceSettingsPage: LoginInstanceSettingsPage;
	utilityPagesPage: UtilityPagesPage;
}>({
	loginInstanceSettingsPage: async ({page}, use) => {
		await use(new LoginInstanceSettingsPage(page));
	},
	utilityPagesPage: async ({page}, use) => {
		await use(new UtilityPagesPage(page));
	},
});

export {utilityPagesPage};
