/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {userAdminMock} from '../mocks/userMock';
import {partnerHelperTest} from './partnerHelperTest';

export const test = mergeTests(apiHelpersTest, partnerHelperTest);

export const partnerSiteTest = test.extend<{partnerSite: Site}>({
	partnerSite: async ({apiHelpers, page, partnerHelper}, use) => {
		await partnerHelper.performLogin(page, userAdminMock);

		const site =
			await apiHelpers.headlessSite.getSiteByERC('LIFERAY_PARTNER');

		await use(site);

		await partnerHelper.performLogout(page);
	},
});
