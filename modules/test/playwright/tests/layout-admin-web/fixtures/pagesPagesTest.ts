/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {StaticPagesPage} from '../pages/StaticPagesPage';
import {UtilityPageConfigurationPage} from '../pages/UtilityPageConfigurationPage';
import {UtilityPagesPage} from '../pages/UtilityPagesPage';

const pagesPagesTest = test.extend<{
	staticPagesPage: StaticPagesPage;
	utilityPageConfigurationPage: UtilityPageConfigurationPage;
	utilityPagesPage: UtilityPagesPage;
}>({
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
