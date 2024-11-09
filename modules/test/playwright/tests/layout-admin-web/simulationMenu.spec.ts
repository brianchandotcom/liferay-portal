/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesPagesTest,
	pageViewModePagesTest
);

test.describe('Screen Size', () => {
	test('View web content is shown in Web Content Display after be added via content panel', async ({
		apiHelpers,
		page,
		simulationMenuPage,
		site,
	}) => {

		// Create page and go to view mode

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// Open simulation panel

		await simulationMenuPage.openSimulationPanel();

		// Assert desktop

		const device = page.locator('.device');

		await page.getByLabel('Desktop').click();

		await expect(device).toHaveCSS('height', '1050px');
		await expect(device).toHaveCSS('width', '1300px');

		// Assert tablet

		await page.getByLabel('Tablet').click();

		await expect(device).toHaveCSS('height', '900px');
		await expect(device).toHaveCSS('width', '808px');

		// Assert mobile

		await page.getByLabel('Mobile').click();

		await expect(device).toHaveCSS('height', '640px');
		await expect(device).toHaveCSS('width', '400px');

		// Assert custom

		await page.getByLabel('Custom').click();

		await expect(device).toHaveCSS('height', '600px');
		await expect(device).toHaveCSS('width', '600px');

		await page.getByLabel('Height(px)').fill('500');
		await page.getByLabel('Width(px)').fill('500');

		await page.getByLabel('Apply Custom Size').click();

		await expect(device).toHaveCSS('height', '500px');
		await expect(device).toHaveCSS('width', '500px');
	});
});
