/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {styleBookPageTest} from '../../fixtures/StyleBookPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
		'LPS-196847': true,
	}),
	isolatedSiteTest,
	loginTest(),
	styleBookPageTest
);

test('checks the correct label for restricted pages in the preview selector', async ({
	apiHelpers,
	page,
	site,
	styleBooksPage,
}) => {

	// Create a content page with only one permission

	const pageName = getRandomString();

	await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Create a stylebook and edit it

	const styleBookName = getRandomString();

	await styleBooksPage.createStyleBook(styleBookName, site.friendlyUrlPath);

	// Check the restricted page label in the preview selector

	await page.getByRole('button', {name: pageName}).click();

	await expect(
		page.getByRole('menuitem', {name: `${pageName} Restricted Page`})
	).toBeVisible();
});
