/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(isolatedSiteTest, loginTest(), styleBookPageTest);

test.beforeEach(async ({site, styleBooksPage}) => {
	await styleBooksPage.goto(site.friendlyUrlPath);
});

test(
	'The management toolbar displays the selected/total items when selecting style books',
	{tag: '@LPD-76373'},
	async ({page, site, styleBooksPage}) => {
		const styleBookCount = 3;

		const styleBookNames = Array.from({length: styleBookCount}).map(() => {
			return getRandomString();
		});

		await test.step('Add custom style books', async () => {
			for (const styleBookName of styleBookNames) {
				await styleBooksPage.create(styleBookName);

				await styleBooksPage.goto(site.friendlyUrlPath);
			}
		});

		await test.step('Select all style books and verify toolbar text', async () => {
			const checkboxes = page.locator('.card').getByRole('checkbox');

			await expect(checkboxes).toHaveCount(styleBookCount);

			await expect(checkboxes.count()).resolves.toBeGreaterThan(1);

			for (let count = 1; count <= styleBookCount; count++) {
				await checkboxes.nth(count - 1).check();

				let expectedText = `${count} of ${styleBookCount} Items Selected`;

				if (count === styleBookCount) {
					expectedText = `All Selected (${expectedText})`;
				}

				await expect(
					page.getByText(expectedText, {exact: true})
				).toBeVisible();
			}
		});
	}
);

test(
	"The management toolbar is enabled if there's, at least, one custom style book",
	{tag: '@LPD-76373'},
	async ({page, site, styleBooksPage}) => {
		const toolbarCheckbox = page.getByRole('checkbox', {
			name: 'Select All',
		});

		const toolbarSearchInput = page.getByRole('searchbox', {
			name: 'Search For',
		});

		await test.step('Management toolbar is disabled initially', async () => {
			await expect(toolbarCheckbox).toBeHidden();
			await expect(toolbarSearchInput).toBeDisabled();
		});

		await test.step('Add custom style books and verify if toolbar is enabled', async () => {
			await styleBooksPage.create(getRandomString());

			await styleBooksPage.goto(site.friendlyUrlPath);

			await expect(toolbarCheckbox).toBeEnabled();
			await expect(toolbarSearchInput).toBeEnabled();
		});
	}
);
