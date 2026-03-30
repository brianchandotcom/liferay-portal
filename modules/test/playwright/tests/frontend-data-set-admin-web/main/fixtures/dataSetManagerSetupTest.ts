/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';

const dataSetManagerSetupTest = test.extend<{
	setup: GlobalMenuPage;
}>({
	setup: [
		async ({page}, use) => {
			const setupPage = new GlobalMenuPage(page);

			await setupPage.goToControlPanel('Data Sets');

			await page.locator('.data-sets').waitFor();

			await use(setupPage);
		},
		{auto: true, scope: 'test'},
	],
});

export {dataSetManagerSetupTest};
