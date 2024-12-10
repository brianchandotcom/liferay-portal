/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';

export const testAdminUser = mergeTests(
	apiHelpersTest,
	loginTest({screenName: 'test'})
);

export const testSupplierUser = mergeTests(
	apiHelpersTest,
	loginTest({screenName: 'demo.unprivileged'})
);

export interface Marketplace {
	marketplace: Site;
}

const SITE_EXTERNAL_REFERENCE_CODE = 'LIFERAY_MARKETPLACE';
const SITE_NAME = 'Marketplace';

async function MarketplaceSiteFixtureBase(apiHelpers, page) {
	const site = await apiHelpers.headlessSite.getSiteByERC(
		SITE_EXTERNAL_REFERENCE_CODE
	);

	if (!site || !site.name || !site.id || !site.friendlyUrlPath) {
		throw new Error(`Site ${SITE_EXTERNAL_REFERENCE_CODE} not found`);
	}

	expect(site.name).toBe(SITE_NAME);
	expect(site.id).toBeGreaterThan(0);

	await page.goto(`web${site.friendlyUrlPath}`);

	return site;
}

export const marketplaceSiteFixture = testAdminUser.extend<Marketplace>({
	marketplace: [
		async ({apiHelpers, page}, use) => {
			const site = await MarketplaceSiteFixtureBase(apiHelpers, page);
			use(site);
		},
		{auto: true},
	],
});

export const marketplaceSiteFixtureSupplierUser =
	testSupplierUser.extend<Marketplace>({
		marketplace: async ({apiHelpers, page}, use) => {
			const site = await MarketplaceSiteFixtureBase(apiHelpers, page);
			use(site);
		},
	});
