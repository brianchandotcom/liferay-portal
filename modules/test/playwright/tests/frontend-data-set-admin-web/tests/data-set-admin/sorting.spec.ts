/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import saveFromModal from '../../utils/saveFromModal';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {sortingPageTest} from './fixtures/sortingPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	sortingPageTest,
	loginTest()
);

let dataSetERC: string;
let dataSetLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Sorting in Data Set Manager', () => {
	test.beforeEach(async ({sortingPage}) => {
		await test.step('Navigate to Sorting section', async () => {
			await sortingPage.goto({
				dataSetLabel,
			});
		});
	});

	test('Save and cancel buttons are not present @LPD-9468', async ({
		page,
	}) => {
		await test.step('Check that save and cancel buttons are not present', async () => {
			await expect(
				page.getByRole('button', {name: 'Save'})
			).not.toBeVisible();
			await expect(
				page.getByRole('button', {name: 'Cancel'})
			).not.toBeVisible();
		});
	});

	test('Sorting options can be reordered and changes are persisted @LPD-9468', async ({
		dataSetManagerApiHelpers,
		sortingPage,
	}) => {
		await test.step('Create sorting options', async () => {
			await dataSetManagerApiHelpers.createDataSetSort({
				dataSetERC,
				defaultValue: true,
				fieldName: 'id',
				label_i18n: {en_US: 'ID'},
				orderType: 'asc',
			});

			await dataSetManagerApiHelpers.createDataSetSort({
				dataSetERC,
				defaultValue: false,
				fieldName: 'name',
				label_i18n: {en_US: 'Name'},
			});

			await dataSetManagerApiHelpers.createDataSetSort({
				dataSetERC,
				defaultValue: false,
				fieldName: 'dateCreated',
				label_i18n: {en_US: 'Date Created'},
			});
		});

		await test.step('Navigate to Sorting section', async () => {
			await sortingPage.goto({
				dataSetLabel,
			});
		});

		await test.step('Check that "Date Created" is below "Name"', async () => {
			const tableLabelCellTexts =
				await sortingPage.getTableLabelCellTexts();

			expect(tableLabelCellTexts).toEqual(['ID', 'Name', 'Date Created']);
		});

		await test.step('Move the "Date Created" option above "Name"', async () => {
			const dateCreatedRow = sortingPage.sortingTable.getByRole('row', {
				name: 'Date Created',
			});

			const nameRow = sortingPage.sortingTable.getByRole('row', {
				name: 'Name',
			});

			await dateCreatedRow.dragTo(nameRow);
		});

		await test.step('Check that "Date Created" is above "Name"', async () => {
			const tableLabelCellTexts =
				await sortingPage.getTableLabelCellTexts();

			expect(tableLabelCellTexts).toEqual(['ID', 'Date Created', 'Name']);
		});

		await test.step('Navigate to the "Details" tab and back to "Sorting" tab', async () => {
			await sortingPage.selectTab('Details');
			await sortingPage.selectTab('Sorting');
		});

		await test.step('Check that the order is still the same', async () => {
			const tableLabelCellTexts =
				await sortingPage.getTableLabelCellTexts();

			expect(tableLabelCellTexts).toEqual(['ID', 'Date Created', 'Name']);
		});
	});

	test('In the New Sort modal, the Order Type input only appears when default is checked @LPD-19465', async ({
		page,
		sortingPage,
	}) => {
		await test.step('Open new sort modal', async () => {
			await sortingPage.openAddSortingModal();
		});

		await test.step('Order Type input only appears when default is checked', async () => {
			await expect(page.getByLabel('Order Type')).not.toBeVisible();

			await page.getByLabel('Use as Default Sorting').check();

			await expect(page.getByLabel('Order Type')).toBeVisible();
		});
	});

	test('Sorting can be created, edited, and deleted @LPD-19465', async ({
		page,
		sortingPage,
	}) => {
		await test.step('Open new sort modal', async () => {
			await sortingPage.openAddSortingModal();
		});

		await test.step('Input values', async () => {
			await page.getByLabel('Label').fill('Date Modified');
			await page.getByLabel('Sort By').selectOption('dateModified');
			await page.getByLabel('Use as Default Sorting').check();
		});

		await test.step('Save changes', async () => {
			await saveFromModal({
				page,
			});
		});

		await test.step('New sort is displayed on the table', async () => {
			await expect(page.getByText('Date Modified').first()).toBeVisible();
			await expect(page.getByText('dateModified').first()).toBeVisible();
			await expect(page.getByText('Yes').first()).toBeVisible();
		});

		await test.step('Open edit sort modal', async () => {
			const tableRow = sortingPage.sortingTable.locator('tr', {
				has: page.locator('text="Date Modified"'),
			});

			await tableRow
				.getByRole('cell', {name: 'Actions'})
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Edit'}).click();
		});

		await test.step('Change label and sort by values', async () => {
			await page.getByLabel('Label').fill('Date Created');
			await page.getByLabel('Sort By').selectOption('dateCreated');
			await page.getByLabel('Use as Default Sorting').setChecked(false);
		});

		await test.step('Save changes', async () => {
			await saveFromModal({
				page,
			});
		});

		await test.step('Edited sort is updated on the table', async () => {
			await expect(page.getByText('Date Created').first()).toBeVisible();
			await expect(page.getByText('dateCreated').first()).toBeVisible();
			await expect(page.getByText('No').first()).toBeVisible();
		});

		await test.step('Delete sort', async () => {
			const tableRow = sortingPage.sortingTable.locator('tr', {
				has: page.locator('text="Date Created"'),
			});

			await tableRow
				.getByRole('cell', {name: 'Actions'})
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await page.getByRole('button', {name: 'Delete'}).click();

			await expect(
				page.getByText('Date Created').first()
			).not.toBeVisible();
		});
	});
});

export const applicationPageTest = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	loginTest()
);

applicationPageTest.describe(
	'Sorting Dropdown in Data Set Application Page',
	() => {
		applicationPageTest(
			'When sorting configuration has no labels defined, the order dropdown is not displayed @LPD-19503',
			async ({dataSetsPage, page}) => {
				await dataSetsPage.goto();

				await expect(
					page.getByRole('button', {name: 'Order'})
				).not.toBeVisible();
			}
		);
	}
);
