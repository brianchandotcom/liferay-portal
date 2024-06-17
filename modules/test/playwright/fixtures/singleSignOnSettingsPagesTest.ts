/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {InstanceSettingsPage} from '../pages/configuration-admin-web/InstanceSettingsPage';
import {SingleSignOnSettingsPage} from '../pages/portal-settings-authentication-openid-connect-web/SingleSignOnSettingsPage';

const instanceSettingsPagesTest = test.extend<{
	instanceSettingsPage: InstanceSettingsPage;
	singleSignOnSettingsPage: SingleSignOnSettingsPage;
}>({
	instanceSettingsPage: async ({page}, use) => {
		await use(new InstanceSettingsPage(page));
	},
	singleSignOnSettingsPage: async ({page}, use) => {
		await use(new SingleSignOnSettingsPage(page));
	},
});

export {instanceSettingsPagesTest};
