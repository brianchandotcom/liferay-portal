/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';
import isSPAEnabled from './utils/isSPAEnabled';

export const test = mergeTests(
	loginTest(),
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	pageViewModePagesTest
);

test(
	'Assert it appends existing temporary styles with id in the same place as the reference upon navigation',
	{
		tag: '@LPD-49303',
	},
	async ({apiHelpers, page, site}) => {
		const firstLayout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: 'LPD-49303-firstLayout',
		});

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: 'LPD-49303-secondLayout',
		});

		await test.step('Naviagate to root page and assert SPA is enabled', async () => {
			await page.goto('/');

			expect(isSPAEnabled({page})).toBeTruthy();
		});

		await test.step('Navigate to first page and assert body background color', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${firstLayout.friendlyURL}`
			);

			const bodyBackgroundColor = await page.evaluate(() => {
				return window.getComputedStyle(document.body)[
					'background-color'
				];
			});

			expect(bodyBackgroundColor).toEqual('rgb(0, 0, 255)');
		});

		await test.step('Navigate to second page and assert styles are applied according to the order in DOM', async () => {

			// Navigate to second page using SPA. If we use page.goto() is loading a new page

			const secondPageMenuItem = page.getByRole('menuitem', {
				name: 'LPD-49303-secondLayout',
			});

			await secondPageMenuItem.click();

			await secondPageMenuItem.evaluate((element) =>
				element.classList.contains('active')
			);

			const bodyBackgroundColor = await page.evaluate(() => {
				return window.getComputedStyle(document.body)[
					'background-color'
				];
			});

			expect(bodyBackgroundColor).toEqual('rgb(0, 0, 255)');

			const bodyColor = await page.evaluate(() => {
				return window.getComputedStyle(document.body)['color'];
			});

			expect(bodyColor).toEqual('rgb(1, 2, 3)');
		});
	}
);
