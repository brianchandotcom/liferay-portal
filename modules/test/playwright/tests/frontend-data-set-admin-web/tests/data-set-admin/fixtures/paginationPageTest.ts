/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {PaginationPage} from '../pages/data_set/tabs/PaginationPage';

const paginationPageTest = test.extend<{
	paginationPage: PaginationPage;
}>({
	paginationPage: async ({page}, use) => {
		await use(new PaginationPage(page));
	},
});

export {paginationPageTest};
