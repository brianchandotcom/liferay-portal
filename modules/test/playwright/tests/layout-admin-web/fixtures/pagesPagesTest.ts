/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {LayoutPage} from '../pages/LayoutPage';
import {PageConfigurationPage} from '../pages/PageConfigurationPage';
import {StaticPagesPage} from '../pages/StaticPagesPage';
import {UtilityPageConfigurationPage} from '../pages/UtilityPageConfigurationPage';
import {UtilityPagesPage} from '../pages/UtilityPagesPage';

const pagesPagesTest = test.extend<{
	layoutPage: LayoutPage;
	pageConfigurationPage: PageConfigurationPage;
	staticPagesPage: StaticPagesPage;
	utilityPageConfigurationPage: UtilityPageConfigurationPage;
	utilityPagesPage: UtilityPagesPage;
}>({
	layoutPage: async ({page}, use) => {
		await use(new LayoutPage(page));
	},
	pageConfigurationPage: async ({page}, use) => {
		await use(new PageConfigurationPage(page));
	},
	staticPagesPage: async ({page}, use) => {
		await use(new StaticPagesPage(page));
	},
	utilityPageConfigurationPage: async ({page}, use) => {
		await use(new UtilityPageConfigurationPage(page));
	},
	utilityPagesPage: async ({page}, use) => {
		await use(new UtilityPagesPage(page));
	},
});

export {pagesPagesTest};
