/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {GlobalMenuPage} from '../pages/product-navigation-applications-menu/GlobalMenuPage';

const globalMenuPagesTest = test.extend<{
	globalMenuPage: GlobalMenuPage;
}>({
	globalMenuPage: async ({page}, use) => {
		await use(new GlobalMenuPage(page));
	},
});

export {globalMenuPagesTest};
