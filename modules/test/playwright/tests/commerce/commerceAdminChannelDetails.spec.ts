/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-13490 Manage channel country visibility from channel page', async ({
	apiHelpers,
	commerceAdminChannelDetailsCountriesPage,
	commerceAdminChannelDetailsPage,
	commerceAdminChannelsPage,
	page,
}) => {
	await page.goto('/');

	const site = await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
		'guest'
	);

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	await commerceAdminChannelsPage.goto();

	await (
		await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
	).click();

	await commerceAdminChannelDetailsPage.goToCountries();

	await commerceAdminChannelDetailsCountriesPage.addCountryButton.click();

	const countryName1 = 'Afghanistan';
	const countryName2 = 'Aland Islands';

	await (
		await commerceAdminChannelDetailsCountriesPage.countryFrameCountry(
			countryName1
		)
	).check();
	await (
		await commerceAdminChannelDetailsCountriesPage.countryFrameCountry(
			countryName2
		)
	).check();

	await commerceAdminChannelDetailsCountriesPage.addCountryAddButton.click();

	await expect(
		(
			await commerceAdminChannelDetailsCountriesPage.countriesTableRow(
				0,
				countryName1,
				true
			)
		).row
	).toBeVisible();
	await expect(
		(
			await commerceAdminChannelDetailsCountriesPage.countriesTableRow(
				0,
				countryName2,
				true
			)
		).row
	).toBeVisible();

	await (
		await commerceAdminChannelDetailsCountriesPage.countriesTableRowAction(
			countryName1,
			'Remove'
		)
	).click();

	await page.reload();

	expect(
		await commerceAdminChannelDetailsCountriesPage.countriesTableRows()
	).toHaveLength(1);
	await expect(
		(
			await commerceAdminChannelDetailsCountriesPage.countriesTableRow(
				0,
				countryName2,
				true
			)
		).row
	).toBeVisible();
});
