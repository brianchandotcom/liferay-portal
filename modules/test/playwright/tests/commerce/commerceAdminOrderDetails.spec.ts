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

test('LPD-15231 Escape account name on admin order details page', async ({
	apiHelpers,
	commerceAdminOrderDetailsPage,
	commerceAdminOrdersPage,
	page,
}) => {
	await page.goto('/');

	const site = await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
		'guest'
	);

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const account = await apiHelpers.headlessAdminUser.postAccount({
		name: '<img src="x" onError="alert(document.location)">',
	});

	apiHelpers.data.push({id: account.id, type: 'account'});

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const cart = await apiHelpers.headlessCommerceDeliveryCart.postCart(
		{
			accountId: account.id,
		},
		channel.id
	);

	await commerceAdminOrdersPage.goto();

	await (await commerceAdminOrdersPage.ordersTableRowLink(cart.id)).click();

	await expect(
		commerceAdminOrderDetailsPage.headerDetailsTitle
	).toBeVisible();

	await expect(
		commerceAdminOrderDetailsPage.commerceOrderAccountEntryName
	).toHaveText(account.name);
});
