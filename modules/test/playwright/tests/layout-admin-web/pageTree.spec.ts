/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
		'LPS-196847': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test('checks the correct label for restricted page in the Page Tree', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission and open the edit mode

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Open the Product Menu

	await page
		.getByRole('tab', {
			name: 'Product Menu',
		})
		.click({timeout: 3000});

	// Check the correct label for restricted page

	await page.getByRole('button', {exact: true, name: 'Page Tree'}).click();

	await expect(
		page
			.getByLabel('Product Menu', {exact: true})
			.locator('div', {
				hasText: pageName,
			})
			.getByLabel('Restricted Page')
	).toBeVisible();
});
