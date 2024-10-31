/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {miniumSetUp} from '../utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test.use({
	...devices['Pixel 5'],
});

test('LPD-3391 Minium sidebar user navigation items are clickable in responsive mode', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceThemeMiniumPage,
	page,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceThemeMiniumPage.stickerUserNav.click();
	await commerceThemeMiniumPage.myProfileItemMenu.click();

	await expect(
		page.getByRole('heading', {name: 'Account Management'})
	).toBeVisible();
});
