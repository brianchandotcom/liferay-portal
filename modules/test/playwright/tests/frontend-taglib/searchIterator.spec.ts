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
import getRandomString from '../../utils/getRandomString';
import {taglibSamplePageTest} from './fixtures/taglibSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest,
	taglibSamplePageTest
);

test(
	'Search Iterator overlaps fixed header on scrolling',
	{tag: '@LPD-40036'},
	async ({apiHelpers, page, site, widgetPagePage}) => {
		await test.step('Select Panel tab', async () => {
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
			
			const bottomestElement = permissionsIframe
				.getByLabel('Pagination')
				.locator('div')
				.nth(1);

			bottomestElement.scrollIntoViewIfNeeded();

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
