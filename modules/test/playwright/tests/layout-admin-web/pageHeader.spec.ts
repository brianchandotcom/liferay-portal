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

test('checks the correct label for restricted page in the page heading', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission

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

	// Go to the view mode and check the restricted page label

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await expect(
		page.getByRole('heading', {name: pageName}).getByText('Restricted Page')
	).toBeVisible();
});
