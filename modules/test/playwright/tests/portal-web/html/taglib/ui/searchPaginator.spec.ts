/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {samplePageTest} from '../../../../frontend-taglib/fixtures/samplePageTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	samplePageTest
);

const linkName = 'Search Paginator';

test(
	'Search Paginator dropdown generates page links on scrolling',
	{tag: '@LPD-37458'},
	async ({page, samplePage, site}) => {
		let dropdownMenuHandler: Locator;

		await test.step('Create a content site and the frontend taglib sample widget', async () => {
			await samplePage.setupSampleWidget({
				site,
			});
		});

		await test.step('Select Panel link', async () => {
			await samplePage.selectLink(linkName);
		});

		await test.step('Open navigator dropdown', async () => {
			await page
				.getByRole('button', {
					name: 'Intermediate Pages Use TAB to',
				})
				.click();

			await expect(
				page.getByRole('menuitem', {exact: true, name: 'Page 4'})
			).toBeVisible();

			dropdownMenuHandler = page
				.getByRole('menu')
				.filter({hasText: '4 5 6 7 8 9 10 11 12 13 14 15'});
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
				dropdownMenuHandler.getByRole('menuitem', {
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
