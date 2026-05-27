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
import {waitForAlert} from '../../../../../utils/waitForAlert';
import {systemDataSetsPageTest} from '../../../../frontend-data-set-admin-web/main/fixtures/systemDataSetsPageTest';
import {SystemDataSetsPage} from '../../../../frontend-data-set-admin-web/main/pages/SystemDataSetsPage';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';
import {FDSSamplePage} from '../../pages/FDSSamplePage';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	systemDataSetsPageTest
);

let fdsSamplePageURL: string;

async function deleteAdvancedSampleCustomization({
	fdsSamplePage,
	systemDataSetsPage,
}: {
	fdsSamplePage: FDSSamplePage;
	systemDataSetsPage: SystemDataSetsPage;
}) {
	await systemDataSetsPage.goto();

	const advancedSampleRow = systemDataSetsPage.pageContainer
		.locator('.fds tr')
		.filter({hasText: 'Advanced Sample'});

	if ((await advancedSampleRow.count()) === 0) {
		return;
	}

	await advancedSampleRow.locator('.dropdown-toggle').click();

	await fdsSamplePage.dropdownMenu
		.getByRole('menuitem', {name: 'Delete'})
		.click();

	const deleteModal = systemDataSetsPage.page.getByRole('dialog');

	await deleteModal.getByRole('button', {name: 'Delete'}).click();

	await waitForAlert(systemDataSetsPage.page);

	await expect(advancedSampleRow).not.toBeAttached();
}

test.beforeEach(async ({fdsSamplePage, page, site, systemDataSetsPage}) => {
	await deleteAdvancedSampleCustomization({
		fdsSamplePage,
		systemDataSetsPage,
	});

	const {url} = await fdsSamplePage.setupFDSSampleWidget({site});

	fdsSamplePageURL = url;

	await fdsSamplePage.selectTab('Advanced');

	await waitForFDS({
		page,
		visualizationMode: EFDSVisualizationMode.TABLE,
	});
});

test('Resize columns', {tag: '@LPD-54497'}, async ({fdsSamplePage, page}) => {
	const firstColumnHeader = fdsSamplePage.table.firstColumnHeader;
	let initialWidth: number;

	await test.step('Get the initial width of a column', async () => {
		initialWidth = await firstColumnHeader.evaluate(
			(element) => element.getBoundingClientRect().width
		);

		await expect(initialWidth).toBeGreaterThan(0);
	});

	await test.step('Drag resizer element to make column wider', async () => {
		const resizer = firstColumnHeader.locator('.dnd-th-resizer');
		const resizerBoundingBox = await resizer.boundingBox();

		const startX = resizerBoundingBox.x + resizerBoundingBox.width / 2;
		const startY = resizerBoundingBox.y + resizerBoundingBox.height / 2;

		await page.mouse.move(startX, startY);
		await page.mouse.down();
		await page.mouse.move(startX + 50, startY, {steps: 5});
		await page.mouse.up();
	});

	await test.step('Check that final widht is greater than initial one', async () => {
		const finalWidth = await firstColumnHeader.evaluate(
			(element) => element.getBoundingClientRect().width
		);

		await expect(finalWidth).toBeGreaterThan(initialWidth);
	});
});

test(
	'Hide column and assert correct visibility of columns',
	{tag: '@LPD-45051'},
	async ({fdsSamplePage, page}) => {
		const firstRowItemActionButton =
			fdsSamplePage.table.itemActionButtons.first();

		const tableCells = fdsSamplePage.table.container.locator('td');

		const initialBodyCellText = await tableCells.nth(1).innerText();

		const rowAction = tableCells.locator('.component-action').first();

		await test.step('Check that row actions are present', async () => {
			await firstRowItemActionButton.hover();

			await expect(rowAction).toBeAttached();
		});

		await test.step('Hide the first column', async () => {
			const button = page.getByLabel('Manage Columns Visibility');

			await expect(button).toBeAttached();

			await button.click();

			const menuItem = page.getByRole('menuitem').nth(0);

			await menuItem.click();
		});

		await test.step('Check that the first column is hidden and the row actions are still present', async () => {
			await expect(tableCells.nth(1)).not.toHaveText(initialBodyCellText);

			await expect(rowAction).toBeAttached();
		});
	}
);

test(
	'Columns with nested field names are shown',
	{tag: '@LPD-75783'},
	async ({fdsSamplePage, page, systemDataSetsPage}) => {
		try {
			await test.step('Check author column, defined by creator.name field name, is visible', async () => {
				await expect(
					fdsSamplePage.table.container.locator(
						'[data-id="string,creator,name"]'
					)
				).toBeVisible();
			});

			await test.step('Create System Data Set', async () => {
				await systemDataSetsPage.goto();

				const creationModal = systemDataSetsPage.creationModal;

				const advancedSampleListItem = creationModal.listItems.filter({
					hasText: 'Advanced Sample',
				});

				await systemDataSetsPage.createButton.click();

				await expect(advancedSampleListItem).toBeVisible();

				await advancedSampleListItem.click();

				await expect(advancedSampleListItem).toHaveClass(/selected/);

				await creationModal.createButton.click();

				await waitForAlert(systemDataSetsPage.page);
			});

			await test.step('Check author column is still visible', async () => {
				await page.goto(fdsSamplePageURL);

				await fdsSamplePage.selectTab('Advanced');

				await waitForFDS({
					page,
					visualizationMode: EFDSVisualizationMode.TABLE,
				});

				await expect(
					fdsSamplePage.table.container.locator(
						'[data-id="string,creator.name"]'
					)
				).toBeVisible();
			});
		}
		finally {
			await test.step('Delete used system data set', async () => {
				await deleteAdvancedSampleCustomization({
					fdsSamplePage,
					systemDataSetsPage,
				});
			});
		}
	}
);
