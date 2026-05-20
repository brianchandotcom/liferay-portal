/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('Advanced');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

test(
	'Sort columns and assert visibility',
	{tag: '@LPS-193005'},
	async ({page}) => {
		await test.step('Sorting ID and Title column in ascending order', async () => {
			const idColumnHeader = page
				.getByRole('columnheader')
				.getByText('ID');

			await expect(idColumnHeader).toBeInViewport();

			await Promise.all([
				idColumnHeader.click(),
				page.waitForResponse(
					(response: any) => response.status() === 200
				),
			]);

			let cells = await page.locator('td').allInnerTexts();

			await expect(page.locator('td').nth(1)).toHaveText(cells[1]);
			await expect(page.locator('td').nth(12)).toHaveText(cells[12]);
			await expect(page.locator('td').nth(23)).toHaveText(cells[23]);
			await expect(page.locator('td').nth(34)).toHaveText(cells[34]);

			const ascendingIDCells = [
				cells[1],
				cells[12],
				cells[23],
				cells[34],
				cells[45],
				cells[56],
				cells[67],
				cells[78],
				cells[89],
				cells[100],
			].sort();

			await expect(page.locator('td').nth(1)).toHaveText(
				ascendingIDCells[0]
			);
			await expect(page.locator('td').nth(12)).toHaveText(
				ascendingIDCells[1]
			);
			await expect(page.locator('td').nth(23)).toHaveText(
				ascendingIDCells[2]
			);
			await expect(page.locator('td').nth(34)).toHaveText(
				ascendingIDCells[3]
			);

			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await Promise.all([
				titleColumnHeader.click(),
				page.waitForResponse(
					(response: any) => response.status() === 200
				),
			]);

			cells = await page.locator('td').allInnerTexts();

			const ascendingTitleCells = [
				cells[2],
				cells[13],
				cells[24],
				cells[35],
				cells[46],
				cells[57],
				cells[68],
				cells[79],
				cells[90],
				cells[101],
			].sort((a, b) => new Intl.Collator('en').compare(a, b));

			await expect(page.locator('td').nth(2)).toHaveText(
				ascendingTitleCells[0]
			);
			await expect(page.locator('td').nth(13)).toHaveText(
				ascendingTitleCells[1]
			);
			await expect(page.locator('td').nth(24)).toHaveText(
				ascendingTitleCells[2]
			);
			await expect(page.locator('td').nth(35)).toHaveText(
				ascendingTitleCells[3]
			);
		});

		await test.step('Hide the Title column', async () => {
			const titleColumnHeader = page
				.getByRole('columnheader')
				.getByText('Title');

			await expect(titleColumnHeader).toBeAttached();

			const button = page.getByLabel('Manage Columns Visibility');

			await expect(button).toBeAttached();

			await button.click();

			const titleMenuItem = page.getByRole('menuitem').nth(1);

			await titleMenuItem.click();

			await expect(
				page.getByRole('columnheader').getByText('Title')
			).toBeHidden();
		});
	}
);
