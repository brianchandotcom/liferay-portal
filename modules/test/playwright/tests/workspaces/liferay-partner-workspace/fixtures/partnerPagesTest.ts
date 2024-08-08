/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {HomePage} from '../pages/HomePage';
import {MDFRequestFormPage} from '../pages/mdf/MDFRequestFormPage';
import {MDFRequestListPage} from '../pages/mdf/MDFRequestListPage';
import {MDFClaimListPage} from '../pages/mdf/mdfClaim/MDFClaimListPage';
import {partnerHelperTest} from './partnerHelperTest';

const test = mergeTests(apiHelpersTest, partnerHelperTest);

export const partnerPagesTest = test.extend<{
	homePage: HomePage;
	mdfRequestFormPage: MDFRequestFormPage;
	mdfRequestListPage: MDFRequestListPage;
	mdfClaimListPage: MDFClaimListPage;
}>({
	homePage: async ({page}, use) => {
		await use(new HomePage(page));
	},
	mdfRequestFormPage: async ({page}, use) => {
		await use(new MDFRequestFormPage(page));
	},
	mdfRequestListPage: async ({page}, use) => {
		await use(new MDFRequestListPage(page));
	},
	mdfClaimListPage: async ({page}, use) => {
		await use(new MDFClaimListPage(page));
	},
});
