/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {OAuth2ApplicationPage} from '../pages/oauth2-provider-web/OAuth2ApplicationPage';

const oauth2ApplicationPagesTest = test.extend<{
	oauth2ApplicationPage: OAuth2ApplicationPage;
}>({
	oauth2ApplicationPage: async ({page}, use) => {
		await use(new OAuth2ApplicationPage(page));
	},
});

export {oauth2ApplicationPagesTest};
