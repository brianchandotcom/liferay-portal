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

export const dsmTest = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest()
);

let dataSetERC: string;
let dataSetLabel: string;

dsmTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
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
			});
		});

		await dsmTest.step('Open add date range filter modal', async () => {
			await filtersPage.openNewFilterModal({
				dropdownItemLabel: 'Date Range',
			});
		});

		const dateFilterOption = filtersPage.page.getByRole('option', {
			name: 'dateCreated',
		});

		await dsmTest.step(
			'Check if date-time filter is available',
			async () => {
				await filtersPage.newDateRangeFilterModal.filterBySelect.click();

				await expect(dateFilterOption).toBeVisible();
			}
		);

		await dsmTest.step(
			'Check date-time filter modal contains from and to fields @LPS-181281',
			async () => {
				await dateFilterOption.click();

				await expect(filtersPage.newDateRangeFilterModal.fromInput).toBeVisible();

				await expect(filtersPage.newDateRangeFilterModal.toInput).toBeVisible();

				await filtersPage.newDateRangeFilterModal.fromDatePickerTrigger.click();

				await expect(filtersPage.newDateRangeFilterModal.datePicker).toBeVisible();

				await filtersPage.page.keyboard.press('Escape');
			}
		);

		await dsmTest.step('Cancel date range filter, check no filters are created @LPS-181281',
			async () => {
				await filtersPage.cancelAddFilterModal();

				await filtersPage.assertFiltersTableRowCount(0);
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
		const fieldLabel = getRandomString();

		const filterLabel = getRandomString();

		async function assertDataIsFetched() {
			await fragmentTest.step(
				'Assert that the data entry is fetched',
				async () => {
					await expect(
						fdsFragmentPage.page.getByText(fieldLabel).first()
					).toBeVisible();
				}
			);
		}

		await fragmentTest.step('Create a new date-time filter', async () => {
			await dataSetManagerApiHelpers.createDataSetDateFilter({
				fieldName: 'dateCreated',
				from: '2020-01-01',
				label_i18n: {en_US: filterLabel},
				r_fdsViewFDSDateFilterRelationship_c_fdsViewERC: dataSetERC,
				to: '3020-01-02',
				type: 'date-time',
			});
		});

		await fragmentTest.step(
			'Add a field, so FDS has something to show',
			async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: fieldLabel},
					name: 'rendererType',
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
			}
		);

		await fragmentTest.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		const activeFilterButton = fdsFragmentPage.page.getByRole('button', {
			name: `${filterLabel}:`,
		});

		await fragmentTest.step(
			'Assert that preloaded filter values are in UI @LPS-191295',
			async () => {
				await expect(activeFilterButton).toBeVisible();
			}
		);

		await assertDataIsFetched();

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

		await fragmentTest.step('Remove the filter @LPS-191295', async () => {
			const removeFilterButton =
				fdsFragmentPage.page.getByLabel('Remove Filter');

			await expect(removeFilterButton).toBeVisible();

			await removeFilterButton.click();
		});

		await assertDataIsFetched();
	}
);
