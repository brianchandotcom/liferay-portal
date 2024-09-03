/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {PageTemplatesPage} from '../pages/layout-page-template-admin-web/PageTemplatesPage';

const pageTemplatesPagesTest = test.extend<{
	pageTemplatesPage: PageTemplatesPage;
}>({
	pageTemplatesPage: async ({page}, use) => {
		await use(new PageTemplatesPage(page));
	},
});

export {pageTemplatesPagesTest};
