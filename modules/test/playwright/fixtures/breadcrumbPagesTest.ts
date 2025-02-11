/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {BreadcrumbPage} from '../pages/site-navigation-breadcrumb-web/BreadcrumbPage';

const breadcrumbPagesTest = test.extend<{
	breadcrumbPage: BreadcrumbPage;
}>({
	breadcrumbPage: async ({page}, use) => {
		await use(new BreadcrumbPage(page));
	},
});

export {breadcrumbPagesTest};
