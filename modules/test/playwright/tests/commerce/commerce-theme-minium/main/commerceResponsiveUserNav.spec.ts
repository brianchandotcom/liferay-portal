/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../../fixtures/productMenuPageTest';
import {miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	productMenuPageTest
);

test.use({
	...devices['Pixel 5'],
});

test('LPD-3391 Minium sidebar user navigation items are clickable in responsive mode', async ({
	apiHelpers,
	commerceThemeMiniumPage,
	page,
	productMenuPage,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await productMenuPage.goToSite(site.name);

	await commerceThemeMiniumPage.stickerUserNav.click();
	await commerceThemeMiniumPage.myProfileItemMenu.click();

	await expect(
		page.getByRole('heading', {name: 'Account Management'})
	).toBeVisible();
});
