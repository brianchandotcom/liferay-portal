/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ViewsPage} from '../pages/ViewsPage';

const viewsPageTest = test.extend<{
	viewsPage: ViewsPage;
}>({
	viewsPage: async ({page}, use) => {
		await use(new ViewsPage(page));
	},
});

export {viewsPageTest};
