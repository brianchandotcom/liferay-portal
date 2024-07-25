/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {HomePage} from '../pages/HomePage';
import {MDFRequestFormPage} from '../pages/mdf/MDFRequestFormPage';
import {MDFRequestListPage} from '../pages/mdf/MDFRequestListPage';
import {partnerSiteTest} from './partnerSiteTest';

export const test = mergeTests(partnerSiteTest);

export interface IPartnerPages {
	homePage: HomePage;
	mdfRequestFormPage: MDFRequestFormPage;
	mdfRequestListPage: MDFRequestListPage;
}

export const partnerPagesTest = test.extend<IPartnerPages>({
	homePage: async ({page}, use) => {
		await use(new HomePage(page));
	},
	mdfRequestFormPage: async ({page}, use) => {
		await use(new MDFRequestFormPage(page));
	},
	mdfRequestListPage: async ({page}, use) => {
		await use(new MDFRequestListPage(page));
	},
});
