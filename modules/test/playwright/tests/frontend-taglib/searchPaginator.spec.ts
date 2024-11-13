/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {taglibSamplePageTest} from './fixtures/taglibSamplePageTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	taglibSamplePageTest
);

const linkName = 'Search Paginator';

test(
	'Search Paginator dropdown generates page links on scrolling',
	{tag: '@LPD-37458'},
	async ({page, site, taglibSamplePage}) => {
		let dropdownMenuHandler: Locator;

		await test.step('Create a content site and the taglib sample widget', async () => {
			await taglibSamplePage.setupTaglibSampleWidget({
				site,
			});
		});

		await test.step('Select Panel link', async () => {
			await taglibSamplePage.selectLink(linkName);
		});

		await test.step('Open navigator dropdown', async () => {
			await page
				.getByRole('button', {
					name: 'Intermediate Pages Use TAB to',
				})
				.click();

			await expect(
				page.getByRole('link', {exact: true, name: 'Page 4'})
			).toBeVisible();

			dropdownMenuHandler = await page.getByText(
				'Page 4 Page 5 Page 6 Page 7'
			);
		});

		await test.step('The dropdown generates page links on scrolling in full size screens', async () => {
			await dropdownMenuHandler.evaluate((element) => {
				element.scrollTop = 600;
			});
			await page
				.getByRole('link', {exact: true, name: 'Page 20'})
				.waitFor();

			await expect(
				dropdownMenuHandler.getByRole('link', {
					exact: true,
					name: 'Page 20',
				})
			).toBeVisible();
		});

		await test.step('Scroll back dropdown to its initial position', async () => {
			await dropdownMenuHandler.evaluate((element) => {
				element.scrollTop = 0;
			});

			await expect(
				dropdownMenuHandler.getByRole('link', {
					exact: true,
					name: 'Page 4',
				})
			).toBeVisible();
		});

		await test.step('The dropdown generates page links on scrolling in narrow screens', async () => {
			await page.setViewportSize({height: 2000, width: 900});

			await dropdownMenuHandler.evaluate((element) => {
				element.scrollTop = 600;
			});
			await page
				.getByRole('link', {exact: true, name: 'Page 20'})
				.waitFor();

			await expect(
				dropdownMenuHandler.getByRole('link', {
					exact: true,
					name: 'Page 20',
				})
			).toBeVisible();
		});
	}
);
