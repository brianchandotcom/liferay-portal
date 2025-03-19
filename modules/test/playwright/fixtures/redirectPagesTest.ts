/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {RedirectPage} from '../pages/redirect-web/RedirectPage';

const redirectPagesTest = test.extend<{
	redirectPage: RedirectPage;
}>({
	redirectPage: async ({page}, use) => {
		await use(new RedirectPage(page));
	},
});

export {redirectPagesTest};
