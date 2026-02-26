/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	instanceSettingsPagesTest,
	loginTest()
);

test(
	'XML Sitemap configuration does not cause issues going to different configuration',
	{
		tag: '@LPD-54034',
	},
	async ({instanceSettingsPage, page}) => {
		await instanceSettingsPage.goToInstanceSetting('SEO', 'XML Sitemap');

		await expect(page.getByText('XML Sitemap Index Enabled')).toBeVisible();

		await page.getByRole('menuitem', {name: 'Friendly URL'}).click();

		await expect(
			page.getByText('URL Separator', {exact: true})
		).toBeVisible();

		await expect(
			page.getByRole('menuitem', {name: 'Friendly URL'})
		).toBeVisible();
	}
);

test(
	'Guest site child sites can be selected within XML Sitemap configuration',
	{
		tag: '@LPD-72680',
	},
	async ({apiHelpers, instanceSettingsPage, page}) => {
		const guestSite = await apiHelpers.headlessSite.getSiteByERC('L_GUEST');

		const childSite = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
			parentSiteKey: guestSite.name,
		});

		apiHelpers.data.push({id: childSite.id, type: 'site'});

		await instanceSettingsPage.goToInstanceSetting('SEO', 'XML Sitemap');

		await page
			.getByLabel('Select Sites Included in the XML Sitemap')
			.click();

		await page
			.frameLocator('iframe[title="Select Site"]')
			.getByRole('link', {name: 'Child Sites'})
			.click();

		await page
			.frameLocator('iframe[title="Select Site"]')
			.getByRole('link', {name: childSite.name})
			.click();

		await expect(page.getByText(childSite.name)).toBeVisible();
	}
);

test(
	'Selecting an already selected site in XML Sitemap does not add a duplicate',
	{
		tag: '@LPD-72680',
	},
	async ({instanceSettingsPage, page}) => {
		await instanceSettingsPage.goToInstanceSetting('SEO', 'XML Sitemap');

		await page
			.getByLabel('Select Sites Included in the XML Sitemap')
			.click();

		await page
			.frameLocator('iframe[title="Select Site"]')
			.getByRole('link', {name: 'Liferay DXP'})
			.click();

		await expect(page.getByText('Liferay DXP')).toHaveCount(1);
	}
);
