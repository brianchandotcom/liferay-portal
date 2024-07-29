/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {picklistApiHelpersTest} from '../../fixtures/picklistApiHelpersTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {filtersPageTest} from './fixtures/filtersPageTest';

const SELECTION_API_HEADLESS_FILTER_NAME = 'Selection API Headless filter';
const SELECTION_PICKLIST_FILTER_NAME = 'Selection Picklist filter';
const SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME =
	'Selection Picklist filter without preselected values';
const PICKLIST_VALUE_KEY_1 = 'sampleValue1';
const PICKLIST_VALUE_NAME_1 = 'Sample Value 1';
const PICKLIST_VALUE_KEY_2 = 'sampleValue2';
const PICKLIST_VALUE_NAME_2 = 'Sample Value 2';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest(),
	dataSetManagerSetupTest,
	picklistApiHelpersTest
);

let dataSetERC: string;
let dataSetLabel: string;
let picklistName: string;

test.beforeEach(
	async ({dataSetManagerApiHelpers, filtersPage, picklistApiHelpers}) => {
		dataSetERC = getRandomString();
		dataSetLabel = getRandomString();
		picklistName = getRandomString();

		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});

		await picklistApiHelpers.createPicklist({
			name: picklistName,
		});

		await picklistApiHelpers.editPicklist({
			key: PICKLIST_VALUE_KEY_1,
			name: picklistName,
			value: PICKLIST_VALUE_NAME_1,
		});

		await picklistApiHelpers.editPicklist({
			key: PICKLIST_VALUE_KEY_2,
			name: picklistName,
			value: PICKLIST_VALUE_NAME_2,
		});

		await test.step('Navigate to the Filters tab', async () => {
			await filtersPage.goto({
				dataSetLabel,
			});
		});
	}
);

test.afterEach(async ({dataSetManagerApiHelpers, picklistApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});

	await picklistApiHelpers.deletePicklist(picklistName);
});

test.describe('Filters in Data Set Manager', () => {
	test('Can create and delete a selection filter from picklist source', async ({
		filtersPage,
		page,
	}) => {
		await test.step('Can not create a selection filter without filling mandatory fields', async () => {
			await filtersPage.openNewFilterModal({
				dropdownItemLabel: 'Selection',
			});

			await filtersPage.saveAddFilterModal();

			await expect(page.getByText('This field is required.')).toHaveCount(
				3
			);

			await filtersPage.cancelAddFilterModal();
		});

		await filtersPage.saveAddFilterModal();

		await expect(page.getByText('This field is required.')).toHaveCount(3);

		await filtersPage.cancelAddFilterModal();
	});

	await test.step('Create a selection filter from picklist source', async () => {
		await filtersPage.createSelectionFilterPicklist({
			filterBy: 'externalReferenceCode',
			filterMode: 'Include',
			name: SELECTION_PICKLIST_FILTER_NAME,
			preselectedValues: [PICKLIST_VALUE_NAME],
			selectionType: 'Single',
			source: picklistName,
			sourceType: 'Object Picklist',
		});

		await filtersPage.saveAddFilterModal();
	});

	await test.step('Check that the selection filter is in the list', async () => {
		await expect(
			page.getByRole('cell', {
				exact: true,
				name: SELECTION_PICKLIST_FILTER_NAME,
				preselectedValues: [PICKLIST_VALUE_NAME_1],
				selectionType: 'Single',
				source: picklistName,
				sourceType: 'Object Picklist',
			});

	await test.step('Create a selection filter from picklist source without preselected values', async () => {
		await filtersPage.createSelectionFilterPicklist({
			filterBy: 'name',
			name: SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME,
			preselectedValues: [],
			selectionType: 'Single',
			source: picklistName,
			sourceType: 'Object Picklist',
		});

		await filtersPage.saveAddFilterModal();
	});

		await test.step('Create a selection filter from picklist source without preselected values', async () => {
			await filtersPage.createSelectionFilterPicklist({
				filterBy: 'name',
				name: SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME,
				preselectedValues: [],
				selectionType: 'Single',
				source: picklistName,
				sourceType: 'Object Picklist',
			});

			await filtersPage.saveAddFilterModal();
		});

		await test.step('Check that the selection filter is also the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME,
				})
			).toBeVisible();
		});

		await test.step('Can search for a filter', async () => {
			await filtersPage.searchInput.click();
			await filtersPage.searchInput.fill(SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME);

			await filtersPage.searchButton.click();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_FILTER_NAME,
				})
			).not
			.toBeVisible();

			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_NO_PRESELECTED_VALUES_FILTER_NAME,
				})
			).toBeVisible();

			await filtersPage.searchInput.click();
			await filtersPage.searchInput.fill('');

			await filtersPage.searchButton.click();
		});

		await test.step('Delete the filter, but cancel action', async () => {
			await filtersPage
				.getRowByText(SELECTION_PICKLIST_FILTER_NAME)
				.locator('.actions-cell button')
				.click();

			const deleteButton = filtersPage.page.getByRole('menuitem', {
				name: 'Delete',
			});

			await expect(deleteButton).toBeInViewport();

			await deleteButton.click();

			const cancelDeleteButton = page.getByRole('button', {name: 'Cancel'});

			await cancelDeleteButton.waitFor();

			await cancelDeleteButton.click();
		});

		await test.step('Check that the selection filter is still in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_FILTER_NAME,
				})
			).toBeVisible();
		});

		await test.step('Delete the filter', async () => {
			await filtersPage
				.getRowByText(SELECTION_PICKLIST_FILTER_NAME)
				.locator('.actions-cell button')
				.click();

			const deleteButton = filtersPage.page.getByRole('menuitem', {
				name: 'Delete',
			});

			await expect(deleteButton).toBeInViewport();

			await deleteButton.click();

			const confirmDeleteButton = page.getByRole('button', {name: 'Delete'});

			await confirmDeleteButton.waitFor();

			await confirmDeleteButton.click();
		});

		await test.step('Check that the selection filter is no longer in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_FILTER_NAME,
				})
			).not.toBeVisible();
		});
	});

	test('Can create a selection filter with API Headless source', async ({
		filtersPage,
		page,
	}) => {
		await test.step('Create a selection filter from API Headless source', async () => {
			await filtersPage.createSelectionFilterApiHeadless({
				filterBy: 'externalReferenceCode',
				filterMode: 'Include',
				itemKey: 'id',
				itemLabel: 'label',
				name: SELECTION_API_HEADLESS_FILTER_NAME,
				preselectedValues: [dataSetLabel],
				restApplication: '/data-set-manager/data-sets',
				restEndpoint: '/',
				restSchema: 'FDSView',
				selectionType: 'Single',
				sourceType: 'API REST Application',
			});

			await filtersPage.saveAddFilterModal();
		});

		await test.step('Check that the selection filter is in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_API_HEADLESS_FILTER_NAME,
				})
			).toBeVisible();
		});
	});

	test('Preselected filter values are checked in the multiSelect', async ({
		filtersPage,
		page,
		picklistApiHelpers,
	}) => {
		const picklist = await picklistApiHelpers.getPicklist(picklistName);
		const preselectedValuesMultiSelect = page.locator(
			'.form-control.form-control-tag-group.input-group'
		);

		await test.step('Create a selection filter', async () => {
			await filtersPage.createSelectionFilterPicklist({
				filterBy: 'externalReferenceCode',
				filterMode: 'Include',
				name: 'Selection Filter',
				preselectedValues: [PICKLIST_VALUE_NAME_1, PICKLIST_VALUE_NAME_2],
				selectionType: 'Single',
				source: picklistName,
				sourceType: 'Object Picklist',
			});

			await filtersPage.saveAddFilterModal();
		});

		await test.step('Open the edit filter modal', async () => {
			await filtersPage.goto({
				dataSetLabel,
			});

			const filterActionsButton = page
				.getByRole('cell', {name: 'Actions'})
				.getByRole('button');

			await expect(filterActionsButton).toBeVisible();

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {name: 'Edit'}),
				trigger: filterActionsButton,
			});

			const dialogFilterSourceSubtitle = page.getByRole('heading', {
				name: 'Filter Source',
			});

			await expect(dialogFilterSourceSubtitle).toBeVisible();

			const dialogFilterOptionsSubtitle = page.getByRole('heading', {
				name: 'Filter Options',
			});

			await expect(dialogFilterOptionsSubtitle).toBeVisible();
		});

		await test.step('Check that the preselected value is checked', async () => {
			await expect(preselectedValuesMultiSelect).toBeVisible();

			await expect(preselectedValuesMultiSelect).toContainText(
				picklist.listTypeEntries[0].name
			);
		});

		await test.step('And checking multiple selection type allows to save more preselected values', async () => {
			await page
				.getByLabel('Preselected Values')
				.waitFor();

			await page
				.getByLabel('Preselected Values')
				.click();

			await page
				.getByRole('option', {name: picklist.listTypeEntries[1].name})
				.waitFor();

			await page
				.getByRole('option', {name: picklist.listTypeEntries[1].name})
				.click();

			await expect( page
				.getByText('Only one value is allowed in "Single" selection mode.') )
				.toBeVisible();

			await page
				.getByText('Multiple')
				.click();

			await expect( page
				.getByText('Only one value is allowed in "Single" selection mode.') )
				.not
				.toBeVisible();

			await filtersPage.saveAddFilterModal();
		})
	});
});
