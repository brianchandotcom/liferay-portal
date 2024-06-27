/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {listTypeDefinitionsPagesTest} from '../../../fixtures/listTypeDefinitionsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	listTypeDefinitionsPagesTest,
	loginTest()
);

test('LPD-22282 Verify UPS client extensions are correctly deployed', async ({
	apiHelpers,
	commerceAdminChannelsPage,
	listTypeDefinitionPage,
	page,
}) => {
	await listTypeDefinitionPage.goto();

	await page.getByRole('link', {exact: true, name: 'UPS Codes'}).click();

	await expect(
		listTypeDefinitionPage.frameLocator.getByText(
			'Showing 1 to 8 of 22 entries.'
		)
	).toBeVisible();

	const site =
		await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath('guest');

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'UPS Channel',
		siteGroupId: site.id,
	});

	await commerceAdminChannelsPage.goto();

	await (
		await commerceAdminChannelsPage.channelsTableRowLink('UPS Channel')
	).click();

	await expect(
		page.getByRole('link', {
			exact: true,
			name: 'Liferay UPS Commerce Shipping Engine',
		})
	).toBeVisible();
});
