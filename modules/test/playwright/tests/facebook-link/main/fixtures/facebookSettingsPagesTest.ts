/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FacebookInstanceSettingsPage} from '../../../../pages/portal-settings-authentication-facebook-connect-web/FacebookInstanceSettingsPage';
import {FacebookSystemSettingsPage} from '../../../../pages/portal-settings-authentication-facebook-connect-web/FacebookSystemSettingsPage';

const facebookSettingsPagesTest = test.extend<{
	facebookInstanceSettingsPage: FacebookInstanceSettingsPage;
	facebookSystemSettingsPage: FacebookSystemSettingsPage;
}>({
	facebookInstanceSettingsPage: async ({page}, use) => {
		await use(new FacebookInstanceSettingsPage(page));
	},
	facebookSystemSettingsPage: async ({page}, use) => {
		await use(new FacebookSystemSettingsPage(page));
	},
});

export {facebookSettingsPagesTest};
