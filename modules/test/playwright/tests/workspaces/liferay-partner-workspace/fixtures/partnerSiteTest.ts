/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {partnerHelperTest} from './partnerHelperTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest({screenName: 'test'}),
	partnerHelperTest
);

export interface IPartnerSite {
	partnerSite: Site;
}

export const partnerSiteTest = test.extend<IPartnerSite>({
	partnerSite: [
		async ({apiHelpers, page}, use) => {
			const site =
				await apiHelpers.headlessSite.getSiteByERC('LIFERAY_PARTNER');

			expect(site.id).toBeGreaterThan(0);

			await page.goto(`web${site.friendlyUrlPath}`, {
				waitUntil: 'commit',
			});

			use(site);
		},
		{auto: true},
	],
});
