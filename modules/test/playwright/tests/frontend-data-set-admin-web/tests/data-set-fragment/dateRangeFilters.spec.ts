/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

let dataSetERC: string;
let dataSetLabel: string;
const DATE_FIELD_NAME = 'dateCreated';

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await test.step('Create a data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test('Date-time filter is displayed in fragment, and applied to data @LPD-10754', async ({
	dataSetManagerApiHelpers,
	fdsFragmentPage,
	layout,
}) => {
	const fieldLabel = getRandomString();

	const filterLabel = getRandomString();

	async function assertDataIsFetched() {
		await test.step('Assert that the data entry is fetched', async () => {
			await expect(
				fdsFragmentPage.page.getByText(fieldLabel).first()
			).toBeVisible();
		});
	}

	await test.step('Create a new date-time filter', async () => {
		await dataSetManagerApiHelpers.createDataSetDateFilter({
			dataSetERC,
			fieldName: DATE_FIELD_NAME,
			from: '2020-01-01',
			label_i18n: {en_US: filterLabel},
			to: '3020-01-02',
			type: 'date-time',
		});
	});

	await test.step('Add a field, so FDS has something to show', async () => {
		await dataSetManagerApiHelpers.createDataSetTableSection({
			dataSetERC,
			fieldName: 'rendererType',
			label_i18n: {en_US: fieldLabel},
			type: 'string',
		});
	});

	await test.step('Configure Data Set fragment', async () => {
		await fdsFragmentPage.configureDataSetFragment({
			dataSetLabel,
			layout,
		});
	});

	const activeFilterButton = fdsFragmentPage.page.getByRole('button', {
		name: `${filterLabel}:`,
	});

	await test.step('Assert that preloaded filter values are in UI @LPS-191295', async () => {
		await expect(activeFilterButton).toBeVisible();
	});

	await assertDataIsFetched();

	await test.step('Set an impossible date range', async () => {
		await activeFilterButton.click();

		const toInput = fdsFragmentPage.page.getByLabel('To', {
			exact: true,
		});

		await expect(toInput).toBeVisible();

		await toInput.click();

		await toInput.fill('2020-01-02');

		const editButton = fdsFragmentPage.page.getByRole('button', {
			name: 'Edit Filter',
		});

		await expect(editButton).toBeVisible();

		await editButton.click();
	});

	await test.step('Assert that the data entry is not fetched', async () => {
		await expect(fdsFragmentPage.emptyStateTitle).toBeVisible();
	});

	await test.step('Remove the filter @LPS-191295', async () => {
		const removeFilterButton =
			fdsFragmentPage.page.getByLabel('Remove Filter');

		await expect(removeFilterButton).toBeVisible();

		await removeFilterButton.click();
	});

	await assertDataIsFetched();
});

test('Can create Date-time filter without start and end dates', async ({
	dataSetManagerApiHelpers,
	fdsFragmentPage,
	layout,
}) => {
	const fieldLabel = getRandomString();
	const filterLabel = getRandomString();

	async function assertDataIsFetched() {
		await test.step('Assert that the data entry is fetched', async () => {
			await expect(
				fdsFragmentPage.page.getByText(fieldLabel).first()
			).toBeVisible();
		});
	}

	await test.step('Create a new date-time filter without start nor end dates', async () => {
		await dataSetManagerApiHelpers.createDataSetDateFilter({
			dataSetERC,
			fieldName: DATE_FIELD_NAME,
			from: '',
			label_i18n: {en_US: filterLabel},
			to: '',
			type: 'date-time',
		});
	});

	await test.step('Add a field, so FDS has something to show', async () => {
		await dataSetManagerApiHelpers.createDataSetTableSection({
			dataSetERC,
			fieldName: 'renderer',
			label_i18n: {en_US: fieldLabel},
			type: 'string',
		});
	});

	await test.step('Configure Data Set fragment', async () => {
		await fdsFragmentPage.configureDataSetFragment({
			dataSetLabel,
			layout,
		});
	});

	await test.step('Assert that the date filter is in the UI', async () => {
		await expect(fdsFragmentPage.fdsFilterButton).toBeVisible();
		await fdsFragmentPage.selectFilter(filterLabel);

		const fromInput = fdsFragmentPage.page.getByLabel('From', {
			exact: true,
		});

		await expect(fromInput).toBeVisible();
		await expect(fromInput).toBeEmpty();

		const toInput = fdsFragmentPage.page.getByLabel('To', {
			exact: true,
		});

		await expect(toInput).toBeVisible();
		await expect(toInput).toBeEmpty();
	});

	await assertDataIsFetched();
});
