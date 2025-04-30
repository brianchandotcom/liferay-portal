/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {navigationMenusPagesTest} from './fixtures/navigationMenusPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest
);

test(
	'Ensure that the IconSelector component can add, edit and delete an icon in a navigation menu item',
	{
		tag: '@LPD-44978',
	},
	async ({apiHelpers, navigationMenusPage, page, site}) => {

		// Create a Page

		const pageName = getRandomString();

		await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});

		// Create a Navigation Menu and Add the Page to it

		await navigationMenusPage.goto(site.friendlyUrlPath);

		await navigationMenusPage.createNavigationMenu(getRandomString());

		await navigationMenusPage.openAddPageModal();

		await navigationMenusPage.pagesModal
			.getByText(pageName, {exact: true})
			.click();

		await navigationMenusPage.selectButton.click();

		// Select an icon for the page item

		await page
			.getByLabel('Open ' + pageName)
			.getByText(pageName)
			.click();

		await navigationMenusPage.addOrChangeIcon('accessibility');

		// Assert that the icon is being displayed in the page menu item

		await expect(
			page.locator('.autofit-col-gutters .lexicon-icon-accessibility')
		).toBeVisible();

		// Select a different icon for the page item

		await navigationMenusPage.addOrChangeIcon('briefcase');

		// Assert that the new icon is being displayed in the page menu item

		await expect(
			page.locator('.autofit-col-gutters .lexicon-icon-briefcase')
		).toBeVisible();

		// Remove the icon from the page item

		await page.getByLabel('Remove icon selection').click();

		await navigationMenusPage.saveButton.click();

		// Assert that the icon is not being displayed in the page menu item

		await expect(
			page.locator('.autofit-col-gutters .lexicon-icon-briefcase')
		).not.toBeVisible();
	}
);
