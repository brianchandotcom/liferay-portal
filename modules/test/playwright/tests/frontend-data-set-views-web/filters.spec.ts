/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {filtersPageTest} from './fixtures/filtersPageTest';
import {viewsPageTest} from './fixtures/viewsPageTest';

export const dsmTest = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest(),
	viewsPageTest
);

let dataSetERC: string;
let dataSetLabel: string;
let dataSetViewERC: string;
let dataSetViewLabel: string;

dsmTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();
	dataSetViewERC = getRandomString();
	dataSetViewLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});

	await dataSetManagerApiHelpers.createDataSetView({
		erc: dataSetViewERC,
		label: dataSetViewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: dataSetERC,
	});
});

dsmTest.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

dsmTest(
	'When creating a new filter in DSM, date-time field is available for selection @LPD-10754',
	async ({filtersPage}) => {
		await dsmTest.step('Navigate to Filters section', async () => {
			await filtersPage.goto({
				dataSetLabel,
				viewLabel: dataSetViewLabel,
			});
		});

		await dsmTest.step('Open add date range filter modal', async () => {
			await filtersPage.openNewFilterModal({
				dropdownItemLabel: 'Date Range',
			});
		});

		await dsmTest.step(
			'Check if date-time filter is available',
			async () => {
				await filtersPage.newDateRangeFilterModal.filterBySelect.click();

				expect(
					filtersPage.page.getByRole('option', {
						name: 'dateCreated',
					})
				).toBeVisible();
			}
		);
	}
);

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false}),
	loginTest()
);

fragmentTest(
	'Date-time filter is displayed in fragment, and applied to data @LPD-10754',
	async ({dataSetManagerApiHelpers, fdsFragmentPage, layout}) => {
		const filterLabel = getRandomString();

		await fragmentTest.step('Create a new date-time filter', async () => {
			await dataSetManagerApiHelpers.createDataSetDateFilter({
				fieldName: 'dateCreated',
				from: '2020-01-01',
				label_i18n: {en_US: filterLabel},
				r_fdsViewFDSDateFilterRelationship_c_fdsViewERC: dataSetViewERC,
				to: '3020-01-02',
				type: 'date-time',
			});
		});

		const fieldLabel = getRandomString();

		await fragmentTest.step(
			'Add a field, so FDS has something to show',
			async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: fieldLabel},
					name: 'rendererType',
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetViewERC,
					type: 'string',
				});
			}
		);

		await fragmentTest.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				layout,
				viewLabel: dataSetViewLabel,
			});
		});

		const activeFilterButton = fdsFragmentPage.page.getByRole('button', {
			name: `${filterLabel}: 1/1/2020 - 1/2/3020`,
		});

		await fragmentTest.step(
			'Assert that preloaded filter values are in UI',
			async () => {
				await expect(activeFilterButton).toBeVisible();
			}
		);

		await fragmentTest.step(
			'Assert that the data entry is fetched',
			async () => {
				await expect(
					fdsFragmentPage.page.getByText(fieldLabel).first()
				).toBeVisible();
			}
		);

		await fragmentTest.step('Set an impossible date range', async () => {
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

		await fragmentTest.step(
			'Assert that the data entry is not fetched',
			async () => {
				await expect(fdsFragmentPage.emptyStateTitle).toBeVisible();
			}
		);
	}
);
