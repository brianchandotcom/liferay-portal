/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../../fixtures/loginTest';
import {liferayConfig} from '../../../../liferay.config';
import {partnerHelperTest} from './partnerHelperTest';

export const test = mergeTests(
	partnerHelperTest,
	loginTest({screenName: 'test'})
);

export interface Partner {
	partner: Site;
}

const SITE_EXTERNAL_REFERENCE_CODE = 'LIFERAY_PARTNER';
const SITE_NAME = 'Liferay Partner';

export const partnerSiteTest = test.extend<Partner>({
	partner: [
		async ({page, partnerHelper}, use) => {
			const site =
				await partnerHelper.apiHelpers.headlessSite.getSiteByERC(
					SITE_EXTERNAL_REFERENCE_CODE
				);

			expect(site.name).toBe(SITE_NAME);
			expect(site.id).toBeGreaterThan(0);

			await page.goto(
				`${liferayConfig.environment.baseUrl}/web${site.friendlyUrlPath}`
			);

			use(site);
		},
		{auto: true},
	],
});
