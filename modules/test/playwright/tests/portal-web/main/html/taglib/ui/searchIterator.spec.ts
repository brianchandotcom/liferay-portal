/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../../../../fixtures/pageViewModePagesTest';
import getRandomString from '../../../../../../utils/getRandomString';
import {samplePageTest} from '../../../../../frontend-taglib/main/fixtures/samplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest,
	samplePageTest
);

const linkName = 'Search Iterator';

test(
	'Search Iterator overlaps fixed header on scrolling',
	{tag: '@LPD-40036'},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		await test.step('Create a content site, add frontend taglib sample widget and open permissions configuration', async () => {
			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: getRandomString(),
			});

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

			await widgetPagePage.addPortlet('Taglib Sample');

			await widgetPagePage.clickOnAction('Taglib Sample', 'Permissions');
		});

		await test.step('Check header sizes', async () => {
			const permissionsIframe = page.frameLocator(
				'iframe[title*="Permissions"]'
			);

			await permissionsIframe.locator('#main-content').hover();

			await page.mouse.wheel(0, 150);

			const mainHeaderWidth = await permissionsIframe
				.locator('.table-responsive')
				.evaluate((element) => element.getBoundingClientRect().width);

			const fixedHeaderWidth = await permissionsIframe
				.locator('.lfr-search-iterator-fixed-header-inner-wrapper')
				.evaluate((element) => element.getBoundingClientRect().width);

			expect(mainHeaderWidth).toBe(fixedHeaderWidth);
		});
	}
);

test(
	'Checkboxes can be selected when using RowChecker',
	{tag: '@LPD-63803'},
	async ({page, samplePage, site}) => {
		await test.step('Create a content site and the frontend taglib sample widget', async () => {
			await samplePage.setupSampleWidget({
				site,
			});
		});

		await test.step('Select Panel link', async () => {
			await samplePage.selectLink(linkName);
		});

		await test.step('Select checkbox and assert it remains marked', async () => {
			const firstCheckbox = page
				.locator(
					'table[data-searchcontainerid*="stringItemSearchContainer"] input[type="checkbox"]'
				)
				.first();

			await firstCheckbox.check();

			expect(firstCheckbox).toBeChecked();
		});
	}
);
