/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {OpenIdInstanceSettingsPage} from '../../../pages/portal-settings-authentication-openid-connect-web/OpenIdInstanceSettingsPage';
import {OpenIdSystemSettingsPage} from '../../../pages/portal-settings-authentication-openid-connect-web/OpenIdSystemSettingsPage';

const openIdSettingsPagesTest = test.extend<{
	openIDInstanceSettingsPage: OpenIdInstanceSettingsPage;
	openIDSystemSettingsPage: OpenIdSystemSettingsPage;
}>({
	openIDInstanceSettingsPage: async ({page}, use) => {
		await use(new OpenIdInstanceSettingsPage(page));
	},
	openIDSystemSettingsPage: async ({page}, use) => {
		await use(new OpenIdSystemSettingsPage(page));
	},
});

export {openIdSettingsPagesTest};
