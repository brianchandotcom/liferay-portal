/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SitesAdminPage} from '../pages/SitesAdminPage';

const sitesAdminPagesTest = test.extend<{
	sitesAdminPage: SitesAdminPage;
}>({
	sitesAdminPage: async ({page}, use) => {
		await use(new SitesAdminPage(page));
	},
});

export {sitesAdminPagesTest};
