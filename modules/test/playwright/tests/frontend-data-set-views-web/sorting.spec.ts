/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {sortingPageTest} from './fixtures/sortingPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';
import saveFromModal from './utils/saveFromModal';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	sortingPageTest,
	loginTest(),
	viewsPageTest
);

let sortingDataSetERC: string;
let sortingDataSetLabel: string;
let sortingDataSetViewERC: string;
let sortingDataSetViewLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	sortingDataSetERC = getRandomString();
	sortingDataSetLabel = getRandomString();
	sortingDataSetViewERC = getRandomString();
	sortingDataSetViewLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: sortingDataSetERC,
		label: sortingDataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: sortingDataSetViewERC,
		label: sortingDataSetViewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: sortingDataSetERC,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: sortingDataSetERC});
});

test.describe('Configure sorting in Data Set Manager', () => {
	test('In the New Sort modal, the Order Type input only appears when default is checked @LPD-19465', async ({
		page,
		sortingPage,
	}) => {
		await test.step('Navigate to Sorting section', async () => {
			await sortingPage.goto({
				dataSetLabel: sortingDataSetLabel,
				viewLabel: sortingDataSetViewLabel,
			});
		});

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
		await test.step('Navigate to Sorting section', async () => {
			await sortingPage.goto({
				dataSetLabel: sortingDataSetLabel,
				viewLabel: sortingDataSetViewLabel,
			});
		});

		await test.step('Open new sort modal', async () => {
			await sortingPage.openAddSortingModal();
		});

		await test.step('Input values', async () => {
			await page.getByLabel('Label').fill('Date Modified');
			await page.getByLabel('Sort By').selectOption('dateModified');
		});

		await test.step('Order Type input only appears when default is checked', async () => {
			await expect(page.getByLabel('Order Type')).not.toBeVisible();

			await page.getByLabel('Use as Default Sorting').check();

			await expect(page.getByLabel('Order Type')).toBeVisible();
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
