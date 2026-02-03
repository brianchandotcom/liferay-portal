/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('React');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

test(
	'Alert as an inline notification component',
	{tag: ['@LPD-75254']},
	async ({page}) => {
		const alert = page.getByRole('alert');

		await test.step('Check that an alert appears below the management bar', async () => {
			await page.getByRole('button', {name: 'Show info message'}).click();

			await expect(alert).toBeInViewport();
			await expect(
				page.getByRole('button', {exact: true, name: 'Reload'})
			).toBeVisible();
			await expect(
				page.getByRole('button', {exact: true, name: 'Dismiss'})
			).toBeVisible();
		});

		await test.step('Check that the "Dismiss" button closes the alert', async () => {
			await page.getByRole('button', {name: 'Show info message'}).click();

			await expect(alert).toBeInViewport();
			await page
				.getByRole('button', {exact: true, name: 'Dismiss'})
				.click();
			await expect(alert).not.toBeInViewport();
		});

		await test.step('Check that the "Close" icon button closes the alert', async () => {
			await page.getByRole('button', {name: 'Show info message'}).click();

			await expect(alert).toBeInViewport();
			await page
				.getByRole('button', {exact: true, name: 'Close'})
				.click();
			await expect(alert).not.toBeInViewport();
		});

		await test.step('Check that the "Reload" button request new data and closes the alert', async () => {
			let cells = await page.locator('td').allInnerTexts();

			await expect(page.locator('td').nth(1)).toHaveText(cells[1]);
			await expect(page.locator('td').nth(10)).toHaveText(cells[10]);
			await expect(page.locator('td').nth(19)).toHaveText(cells[19]);
			await expect(page.locator('td').nth(28)).toHaveText(cells[28]);

			const ascendingIDCells = [
				cells[1],
				cells[10],
				cells[19],
				cells[28],
			];

			await expect(page.locator('td').nth(1)).toHaveText(
				ascendingIDCells[0]
			);
			await expect(page.locator('td').nth(10)).toHaveText(
				ascendingIDCells[1]
			);
			await expect(page.locator('td').nth(19)).toHaveText(
				ascendingIDCells[2]
			);
			await expect(page.locator('td').nth(28)).toHaveText(
				ascendingIDCells[3]
			);

			await page.getByRole('button', {name: 'Show info message'}).click();

			await expect(alert).toBeInViewport();
			await Promise.all([
				page.getByRole('button', {exact: true, name: 'Reload'}).click(),
				page.waitForResponse(
					(response: any) => response.status() === 200
				),
			]);

			await waitForFDS({
				page,
				visualizationMode: EFDSVisualizationMode.TABLE,
			});

			cells = await page.locator('td').allInnerTexts();

			const descendingIDCells = [
				cells[1],
				cells[10],
				cells[19],
				cells[28],
			];

			await expect(page.locator('td').nth(1)).toHaveText(
				descendingIDCells[0]
			);
			await expect(page.locator('td').nth(10)).toHaveText(
				descendingIDCells[1]
			);
			await expect(page.locator('td').nth(19)).toHaveText(
				descendingIDCells[2]
			);
			await expect(page.locator('td').nth(28)).toHaveText(
				descendingIDCells[3]
			);

			await expect(alert).not.toBeInViewport();
		});
	}
);
