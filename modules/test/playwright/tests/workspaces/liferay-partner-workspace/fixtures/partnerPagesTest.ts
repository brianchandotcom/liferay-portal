/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {HomePage} from '../pages/HomePage';
import {MDFRequestFormPage} from '../pages/mdf/MDFRequestFormPage';
import {MDFRequestListPage} from '../pages/mdf/MDFRequestListPage';
import {partnerHelperTest} from './partnerHelperTest';

const test = mergeTests(partnerHelperTest);

export const partnerPagesTest = test.extend<{
	homePage: HomePage;
	mdfRequestFormPage: MDFRequestFormPage;
	mdfRequestListPage: MDFRequestListPage;
}>({
	homePage: async ({partnerHelper}, use) => {
		await use(new HomePage(partnerHelper));
	},
	mdfRequestFormPage: async ({partnerHelper}, use) => {
		await use(new MDFRequestFormPage(partnerHelper));
	},
	mdfRequestListPage: async ({partnerHelper}, use) => {
		await use(new MDFRequestListPage(partnerHelper));
	},
});
