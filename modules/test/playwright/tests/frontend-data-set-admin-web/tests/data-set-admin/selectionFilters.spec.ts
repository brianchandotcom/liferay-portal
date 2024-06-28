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

const SELECTION_PICKLIST_FILTER_NAME = 'Selection Picklist filter';
const SELECTION_API_HEADLESS_FILTER_NAME = 'Selection API Headless filter';
const PICKLIST_VALUE_KEY = 'sampleValue';
const PICKLIST_VALUE_NAME = 'Sample Value';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-10754': true,
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
			key: PICKLIST_VALUE_KEY,
			name: picklistName,
			value: PICKLIST_VALUE_NAME,
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
	test('Can create a selection filter from picklist source', async ({
		filtersPage,
		page,
	}) => {
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
		});

		await test.step('Check that the selection filter is in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_PICKLIST_FILTER_NAME,
				})
			).toBeVisible();
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
		await test.step('Create a selection filter', async () => {
			await filtersPage.createSelectionFilterPicklist({
				filterBy: 'externalReferenceCode',
				filterMode: 'Include',
				name: 'Selection Filter',
				preselectedValues: [PICKLIST_VALUE_NAME],
				selectionType: 'Single',
				source: picklistName,
				sourceType: 'Object Picklist',
			});
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
			const picklist = await picklistApiHelpers.getPicklist(picklistName);

			const preselectedValuesMultiSelect = page.locator(
				'.form-control.form-control-tag-group.input-group'
			);

			await expect(preselectedValuesMultiSelect).toBeVisible();

			await expect(preselectedValuesMultiSelect).toContainText(
				picklist.listTypeEntries[0].name
			);

			const preselectedValueOption = page.getByRole('option', {
				name: 'Sample Value',
			});

			await clickAndExpectToBeVisible({
				target: preselectedValueOption,
				trigger: preselectedValuesMultiSelect,
			});

			const preselectedValueOptionCheckbox =
				preselectedValueOption.locator(
					'.custom-control-input.invisible'
				);

			await expect(preselectedValueOptionCheckbox).toBeChecked();
		});
	});
});
