/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {miniumSetUp} from '../../utils/commerce';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test(
	'Create new order button in minium theme is disabled if max open account orders number is reached',
	{tag: ['@COMMERCE-6215', '@LPD-56172']},
	async ({
		apiHelpers,
		commerceAdminChannelDetailsPage,
		commerceAdminChannelsPage,
		commerceThemeMiniumCatalogPage,
		page,
	}) => {
		const {channel, site} = await miniumSetUp(apiHelpers);

		const account = await apiHelpers.headlessAdminUser.postAccount({
			name: getRandomString(),
			type: 'business',
		});

		await apiHelpers.headlessCommerceAdminOrder.postOrder({
			accountId: account.id,
			channelId: channel.id,
			name: 'order1',
			orderStatus: '2',
		});

		await commerceAdminChannelsPage.goto();

		await (
			await commerceAdminChannelsPage.channelsTableRowLink(channel.name)
		).click();

		await commerceAdminChannelDetailsPage.maxOpenOrderAccountInput.fill(
			'1'
		);
		await commerceAdminChannelDetailsPage.saveButton.click();

		await page.goto(`/web/${site.name}`);

		await commerceThemeMiniumCatalogPage.accountSelectorButton.click();

		await expect(
			await commerceThemeMiniumCatalogPage.createNewOrderButton
		).toBeDisabled();
	}
);
