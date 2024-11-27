/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {customDataSetsPageTest} from './fixtures/customDataSetsPageTest';
import {filtersPageTest} from './fixtures/filtersPageTest';

const test = mergeTests(
	dataSetManagerApiHelpersTest,
	customDataSetsPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest()
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

	await test.step('Check if date-time filter is available', async () => {
		await filtersPage.newDateRangeFilterForm.filterBySelectButton.click();

		await expect(
			filtersPage.fieldSelectModalPage.getFieldCheckboxByLabel(
				DATE_FIELD_NAME
			)
		).toBeEnabled();

		await filtersPage.fieldSelectModalPage.addFieldsDialog.cancelButton.click();
	});

	await test.step('Check date-time filter form contains from and to fields @LPS-181281', async () => {
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

		await expect(filterBySelect).toBeDisabled();

		await expect(
			filtersPage.newDateRangeFilterForm.filterBySelectButton
		).toBeDisabled();
	});

	await test.step('Assert date range correctness @LPS-183056', async () => {
		await filtersPage.newDateRangeFilterForm.fromInput.fill('2001-12-10');

		await filtersPage.newDateRangeFilterForm.toInput.fill('2001-12-09');

		await filtersPage.assertValidationError('Date range is invalid');

		await filtersPage.cancelAddFilterForm();
	});
});

test(
	'No date filters can be created on an already used field',
	{tag: '@LPS-190851'},
	async ({filtersPage}) => {
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

		await test.step('Try to create a date range filter for the same field', async () => {
			await filtersPage.createDateRangeFilter({
				filterBy: DATE_FIELD_NAME,
				name: DATE_FILTER_NAME,
			});

			await filtersPage.saveAddFilterForm();

			await filtersPage.assertValidationError(
				'This field is being used by another filter'
			);
		});
	}
);

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

	await test.step('Try to create a date range filter', async () => {
		await filtersPage.openNewFilterForm({
			dropdownItemLabel: 'Date Range',
			expectSaveHidden: true,
		});

		await expect(
			filtersPage.newDateRangeFilterForm.modalBody
		).toContainText(
			'There are no fields compatible with this type of filter.'
		);

		await filtersPage.closeAddFilterForm();
	});
});

test('Date filters can be reordered', async ({
	dataSetManagerApiHelpers,
	filtersPage,
	page,
}) => {
	const createdDateFilterData = {
		fieldName: DATE_FIELD_NAME,
		name: DATE_FILTER_NAME,
		type: 'Date Filter',
	};

	const updatedDateFilterData = {
		fieldName: 'dateModified',
		name: 'Update date',
		type: 'Date Filter',
	};

	await test.step('Create a couple of date filters', async () => {
		await dataSetManagerApiHelpers.createDataSetDateFilter({
			dataSetERC,
			fieldName: createdDateFilterData.fieldName,
			from: '2020-01-01',
			label_i18n: {en_US: createdDateFilterData.name},
			to: '3020-01-02',
			type: 'date-time',
		});

		await dataSetManagerApiHelpers.createDataSetDateFilter({
			dataSetERC,
			fieldName: updatedDateFilterData.fieldName,
			from: '2020-01-01',
			label_i18n: {en_US: updatedDateFilterData.name},
			to: '3020-01-02',
			type: 'date',
		});
	});

	await test.step('Check current order of date filters', async () => {
		await filtersPage.goto({dataSetLabel});
		await filtersPage.assertFiltersTableRowCount(2);

		await filtersPage.assertTableCellContent({
			filterData: createdDateFilterData,
			page: filtersPage.page,
			rowIndex: 0,
		});

		await filtersPage.assertTableCellContent({
			filterData: updatedDateFilterData,
			page: filtersPage.page,
			rowIndex: 1,
		});
	});

	await test.step('Move second date filter to the top', async () => {
		const secondRow = filtersPage.page
			.locator('.orderable-table-row')
			.nth(1);

		const firstRow = filtersPage.page
			.locator('.orderable-table-row')
			.nth(0);

		await secondRow.dragTo(firstRow);
	});

	await test.step('Check that the filters order has changed', async () => {
		await filtersPage.assertTableCellContent({
			filterData: createdDateFilterData,
			page,
			rowIndex: 1,
		});

		await filtersPage.assertTableCellContent({
			filterData: updatedDateFilterData,
			page,
			rowIndex: 0,
		});
	});

	await test.step('Reload and check that the filters keep the last order saved', async () => {
		await filtersPage.goto({dataSetLabel});

		await filtersPage.assertTableCellContent({
			filterData: createdDateFilterData,
			page,
			rowIndex: 1,
		});

		await filtersPage.assertTableCellContent({
			filterData: updatedDateFilterData,
			page,
			rowIndex: 0,
		});
	});
});
