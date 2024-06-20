/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, loginTest({screenName: 'test'}));

export interface Partner {
	partner: Site;
}

const SITE_EXTERNAL_REFERENCE_CODE = 'LIFERAY_PARTNER';
const SITE_NAME = 'Liferay Partner';

export const partnerSiteFixture = test.extend<Partner>({
	partner: [
		async ({apiHelpers, page}, use) => {
			const site = await apiHelpers.headlessSite.getSiteByERC(
				SITE_EXTERNAL_REFERENCE_CODE
			);

			expect(site.name).toBe(SITE_NAME);
			expect(site.id).toBeGreaterThan(0);

			await page.goto(`web${site.friendlyUrlPath}`);

			use(site);
		},
		{auto: true},
	],
});
