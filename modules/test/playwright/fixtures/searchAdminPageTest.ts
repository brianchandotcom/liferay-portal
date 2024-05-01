/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {SearchAdminPage} from '../pages/portal-search-admin-web/SearchAdminPage';

const searchAdminPageTest = test.extend<{
	searchAdminPage: SearchAdminPage;
}>({
	searchAdminPage: async ({page}, use) => {
		await use(new SearchAdminPage(page));
	},
});

export {searchAdminPageTest};
