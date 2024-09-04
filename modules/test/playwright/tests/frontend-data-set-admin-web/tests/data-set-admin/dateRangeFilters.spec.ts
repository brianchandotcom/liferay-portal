/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {filtersPageTest} from './fixtures/filtersPageTest';

const test = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPD-25905': false,
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest()
);

const modalFieldTest = mergeTests(
	test,
	featureFlagsTest({
		'LPD-25905': true,
	})
);

const dataSetERCs = [];

let dataSetERC: string;
let dataSetLabel: string;
const DATE_FIELD_NAME = 'dateCreated';
const DATE_FILTER_NAME = 'Creation Date';
const NAME_COLUMN_INDEX = 1;

test.beforeEach(async ({dataSetManagerApiHelpers, filtersPage}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();
	dataSetERCs.push(dataSetERC);
	await test.step('Create a data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});

	await test.step('Navigate to Filters section', async () => {
		await filtersPage.goto({
			dataSetLabel,
		});
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	for (const DATA_SET_ERC of dataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc: DATA_SET_ERC,
		});
	}

	dataSetERCs.length = 0;
});

test('When creating a new filter in DSM, date-time field is available for selection @LPD-10754', async ({
	filtersPage,
}) => {
	await test.step('Open add date range filter form', async () => {
		await filtersPage.openNewFilterForm({
			dropdownItemLabel: 'Date Range',
		});
	});

	const dateFilterOption = filtersPage.page.getByRole('option', {
		name: DATE_FIELD_NAME,
	});

	await test.step('Check if date-time filter is available', async () => {
		await filtersPage.newDateRangeFilterForm.filterBySelect.click();

		await expect(dateFilterOption).toBeVisible();
	});

	await test.step('Check date-time filter form contains from and to fields @LPS-181281', async () => {
		await dateFilterOption.click();

		await expect(
			filtersPage.newDateRangeFilterForm.fromInput
		).toBeVisible();

		await expect(filtersPage.newDateRangeFilterForm.toInput).toBeVisible();

		await filtersPage.newDateRangeFilterForm.fromDatePickerTrigger.click();

		await expect(
			filtersPage.newDateRangeFilterForm.datePicker
		).toBeVisible();

		await filtersPage.page.keyboard.press('Escape');
	});

	await test.step('Cancel date range filter, check no filters are created @LPS-181281', async () => {
		await filtersPage.cancelAddFilterForm();

		await filtersPage.assertFiltersTableRowCount(0);
	});
});

test('Ability to save and edit DSM date filters @LPS-181281', async ({
	filtersPage,
}) => {
	const filterNewName = 'Date Created';

	await test.step('Create a date range filter', async () => {
		await filtersPage.createDateRangeFilter({
			filterBy: DATE_FIELD_NAME,
			name: DATE_FILTER_NAME,
		});

		await filtersPage.saveAddFilterForm();
	});

	await test.step('Assert filter is saved', async () => {
		await expect(
			filtersPage
				.getRowByText(DATE_FILTER_NAME)
				.locator('td')
				.nth(NAME_COLUMN_INDEX)
		).toHaveText(DATE_FILTER_NAME);

		await filtersPage.assertFiltersTableRowCount(1);
	});

	await test.step('Check cancel edition causes no changes', async () => {
		await filtersPage
			.getRowByText(DATE_FILTER_NAME)
			.locator('.actions-cell button')
			.click();

		const editButton = filtersPage.page.getByRole('menuitem', {
			name: 'Edit',
		});

		await expect(editButton).toBeInViewport();

		await editButton.click();

		const nameInput = filtersPage.newDateRangeFilterForm.nameInput;

		await expect(nameInput).toBeInViewport();

		await expect(nameInput).toBeEnabled();

		await nameInput.fill(filterNewName);

		await filtersPage.cancelAddFilterForm();

		await filtersPage
			.getRowByText(DATE_FILTER_NAME)
			.locator('.actions-cell button')
			.click();

		await expect(editButton).toBeInViewport();

		await editButton.click();

		await expect(nameInput).toHaveValue(DATE_FILTER_NAME);

		await filtersPage.cancelAddFilterForm();

		await filtersPage.assertFiltersTableRowCount(1);
	});

	await test.step('Edit the filter, change its label @LPS-183056', async () => {
		await filtersPage
			.getRowByText(DATE_FILTER_NAME)
			.locator('.actions-cell button')
			.click();

		const editButton = filtersPage.page.getByRole('menuitem', {
			name: 'Edit',
		});

		await expect(editButton).toBeInViewport();

		await editButton.click();

		const nameInput = filtersPage.newDateRangeFilterForm.nameInput;

		await expect(nameInput).toBeInViewport();

		await expect(nameInput).toBeEnabled();

		await nameInput.fill(filterNewName);

		await filtersPage.saveAddFilterForm();
	});

	await test.step('Assert filter with new name is saved @LPS-183056', async () => {
		await expect(
			filtersPage
				.getRowByText(filterNewName)
				.locator('td')
				.nth(NAME_COLUMN_INDEX)
		).toHaveText(filterNewName);

		await filtersPage.assertFiltersTableRowCount(1);
	});

	await test.step('Assert "filter by" is disabled when editing a filter @LPS-183056', async () => {
		await filtersPage
			.getRowByText(filterNewName)
			.locator('.actions-cell button')
			.click();

		const editButton = filtersPage.page.getByRole('menuitem', {
			name: 'Edit',
		});

		await expect(editButton).toBeInViewport();

		await editButton.click();

		const filterBySelect =
			filtersPage.newDateRangeFilterForm.filterBySelect;

		await filterBySelect.click();

		const filterByDropdown =
			filtersPage.newDateRangeFilterForm.filterByDropdown;

		await expect(filterByDropdown).toBeVisible();

		for (const option of await filterByDropdown.getByRole('button').all()) {
			await expect(option).toBeDisabled();
		}

		await filtersPage.page.keyboard.press('Escape');

		await expect(filterByDropdown).not.toBeVisible();
	});

	await test.step('Assert date range correctness @LPS-183056', async () => {
		await filtersPage.newDateRangeFilterForm.fromInput.fill('2001-12-10');

		await filtersPage.newDateRangeFilterForm.toInput.fill('2001-12-09');

		await filtersPage.assertValidationError('Date range is invalid');

		await filtersPage.cancelAddFilterForm();
	});

	await test.step('Check a filter can not be created on an already used field @LPS-190851', async () => {
		await filtersPage.openNewFilterForm({
			dropdownItemLabel: 'Date Range',
		});

		await filtersPage.newDateRangeFilterForm.filterBySelect.click();

		const dateFilterOption = filtersPage.page.getByRole('option', {
			name: DATE_FIELD_NAME,
		});

		await expect(dateFilterOption).toContainText('In Use');

		await dateFilterOption.click();

		await filtersPage.assertValidationError(
			'This field is being used by another filter'
		);
	});
});

test('No date filters can be created if schema has no date fields', async ({
	dataSetManagerApiHelpers,
	filtersPage,
}) => {
	const dataSetERC = getRandomString();
	const dataSetLabel = 'No date dataset';

	dataSetERCs.push(dataSetERC);

	await test.step('Create Data Set with no date fields', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
			restApplication: '/headless-delivery/v1.0',
			restEndpoint: '/v1.0/sites/{siteId}/blog-posting-images',
			restSchema: 'BlogPostingImage',
		});
	});

	await test.step('Navigate to Filters section', async () => {
		await filtersPage.goto({
			dataSetLabel,
		});
	});

	await test.step('Create a date range filter', async () => {
		await filtersPage.openNewFilterForm({
			dropdownItemLabel: 'Date Range',
			expectSaveHidden: true,
		});
	});

	await test.step('Create a date range filter', async () => {
		await expect(
			filtersPage.newDateRangeFilterForm.modalBody
		).toContainText(
			'There are no fields compatible with this type of filter.'
		);

		await filtersPage.closeAddFilterForm();
	});
});

modalFieldTest(
	'Can create and edit a date range filter in DSM using the field selection modal',
	{tag: '@LPD-25905'},
	async ({filtersPage, page}) => {
		const filterNewName = 'Date Created';

		await modalFieldTest.step('Create a data range filter', async () => {
			await filtersPage.createDateRangeFilter({
				filterBy: 'dateCreated',
				from: '2021-01-01',
				name: DATE_FILTER_NAME,
				to: '2021-12-31',
				useFieldSelectionModal: true,
			});

			await filtersPage.saveAddFilterForm();
		});

		await modalFieldTest.step(
			'Check that the selection filter is in the list',
			async () => {
				await expect(
					page.getByRole('cell', {
						exact: true,
						name: DATE_FILTER_NAME,
					})
				).toBeVisible();
			}
		);

		await modalFieldTest.step(
			'Edit the filter, change its label',
			async () => {
				await filtersPage
					.getRowByText(DATE_FILTER_NAME)
					.locator('.actions-cell button')
					.click();

				const editButton = filtersPage.page.getByRole('menuitem', {
					name: 'Edit',
				});

				await expect(editButton).toBeInViewport();

				await editButton.click();

				const nameInput = filtersPage.newDateRangeFilterForm.nameInput;

				await expect(nameInput).toBeInViewport();

				await expect(nameInput).toBeEnabled();

				await nameInput.fill(filterNewName);

				await filtersPage.saveAddFilterForm();
			}
		);

		await modalFieldTest.step(
			'Assert filter with new name is saved @LPS-183056',
			async () => {
				await expect(
					filtersPage
						.getRowByText(filterNewName)
						.locator('td')
						.nth(NAME_COLUMN_INDEX)
				).toHaveText(filterNewName);

				await filtersPage.assertFiltersTableRowCount(1);
			}
		);
	}
);
