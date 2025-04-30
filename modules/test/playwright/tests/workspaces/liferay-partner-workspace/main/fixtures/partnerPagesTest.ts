/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {HomePage} from '../pages/HomePage';
import {MDFClaimListPage} from '../pages/mdf/MDFClaimListPage';
import {MDFRequestFormPage} from '../pages/mdf/MDFRequestFormPage';
import {MDFRequestListPage} from '../pages/mdf/MDFRequestListPage';
import {partnerHelperTest} from './partnerHelperTest';

const test = mergeTests(apiHelpersTest, partnerHelperTest);

export const partnerPagesTest = test.extend<{
	homePage: HomePage;
	mdfClaimListPage: MDFClaimListPage;
	mdfRequestFormPage: MDFRequestFormPage;
	mdfRequestListPage: MDFRequestListPage;
}>({
	homePage: async ({page}, use) => {
		await use(new HomePage(page));
	},
	mdfClaimListPage: async ({page}, use) => {
		await use(new MDFClaimListPage(page));
	},
	mdfRequestFormPage: async ({page}, use) => {
		await use(new MDFRequestFormPage(page));
	},
	mdfRequestListPage: async ({page}, use) => {
		await use(new MDFRequestListPage(page));
	},
});
