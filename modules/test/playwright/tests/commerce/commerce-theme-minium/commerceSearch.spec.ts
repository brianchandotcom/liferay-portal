/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

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

test('LPD-29997 Search for products by typing different specification values in global search', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceThemeMiniumPage,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceThemeMiniumPage.globalSearchButton.click();

	await commerceThemeMiniumPage.globalSearchInput.click();

	await commerceThemeMiniumPage.globalSearchInput.fill('Plastic');

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem(
			'Timing Chain Tensioner'
		)
	).toBeVisible();

	await commerceThemeMiniumPage.globalSearchClearButton.click();

	await commerceThemeMiniumPage.globalSearchInput.fill('Plastic, Ceramic');

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem(
			'Timing Chain Tensioner'
		)
	).toBeVisible();

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem(
			'Premium Brake Pads'
		)
	).toBeVisible();
});

test('LPD-30191 Search for products by typing different SKUs in global search', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceThemeMiniumPage,
}) => {
	const {site} = await miniumSetUp(apiHelpers);

	await applicationsMenuPage.goToSite(site.name);

	await commerceThemeMiniumPage.globalSearchButton.click();

	await commerceThemeMiniumPage.globalSearchInput.click();

	await commerceThemeMiniumPage.globalSearchInput.fill('MIN93015');

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem('ABS Sensor')
	).toBeVisible();

	await commerceThemeMiniumPage.globalSearchClearButton.click();

	await commerceThemeMiniumPage.globalSearchInput.fill('MIN93015 MIN55861');

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem('ABS Sensor')
	).toBeVisible();

	await expect(
		await commerceThemeMiniumPage.globalSearchSuggestionsItem('U-Joint')
	).toBeVisible();
});
