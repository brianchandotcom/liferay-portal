/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'Checkout with Shipping Engine CX enabled only',
	{tag: ['@LPD-77895']},
	async ({
		apiHelpers,
		checkoutPage,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceMiniCartPage,
		commerceThemeMiniumCatalogPage,
		page,
	}) => {
		test.setTimeout(180000);

		const {channel, site} = await miniumSetUp(apiHelpers);

		await commerceAdminChannelsPage.goto();

		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();

		await commerceAdminChannelDetailsPage.deactivateChannelConfiguration(
			'Flat Rate',
			'Shipping Methods'
		);

		await commerceAdminChannelDetailsPage.activateChannelConfiguration(
			'Sample Shipping Engine',
			'Shipping Methods'
		);

		await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'business',
		});

		await page.goto(`/web/${site.name}/catalog`);

		await commerceThemeMiniumCatalogPage.addToCart('Mount');

		await commerceMiniCartPage.miniCartButton.click();

		await commerceMiniCartPage.submitButton.click();

		await checkoutPage.addAddress({
			city: 'testCity',
			countryLabel: 'United States',
			name: 'John Doe',
			regionLabel: 'Florida',
			street: 'testStreet',
			zip: '12345',
		});

		await checkoutPage.continueButton.click();

		await expect(page.getByText('CXOption1 (+$ 13.90)')).toBeVisible();
		await expect(page.getByText('CXOption2 (+$ 15.99)')).toBeVisible();
	}
);
