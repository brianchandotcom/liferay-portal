/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesPagesTest,
	pageViewModePagesTest
);

test.describe('Page content', () => {
	test(
		'View info messages',
		{
			tag: ['@LPS-186155', '@LPS-187159'],
		},
		async ({apiHelpers, page, simulationMenuPage, site}) => {

			// Create page and go to view mode

			const layout = await apiHelpers.headlessDelivery.createSitePage({
				pageDefinition: getPageDefinition(),
				siteId: site.id,
				title: getRandomString(),
			});

			await page.goto(
				`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
			);

			// Open simulation panel

			await simulationMenuPage.openSimulationPanel();

			// Assert empty messages

			await expect(
				page.getByText('Showing content for the segment "Anyone".')
			).toBeVisible();

			await expect(
				page.getByText(
					'No segments have been added yet. To add a new segment go to Product Menu > People > Segments.'
				)
			).toBeVisible();

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.locator('#experiences'),
				trigger: page.getByRole('combobox', {name: 'Preview By'}),
			});

			await expect(
				page.getByText('Showing content for the experience "Default".')
			).toBeVisible();

			await expect(
				page.getByText('No experiences have been added yet.')
			).toBeVisible();
		}
	);
});

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
