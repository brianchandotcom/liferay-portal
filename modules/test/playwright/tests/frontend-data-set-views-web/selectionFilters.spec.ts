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
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {filtersPageTest} from './fixtures/filtersPageTest';
import {picklistApiHelpersTest} from './fixtures/picklistApiHelpersTest';

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

test.beforeEach(async ({dataSetManagerApiHelpers, picklistApiHelpers}) => {
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
});

test.afterEach(async ({dataSetManagerApiHelpers, picklistApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});

	await picklistApiHelpers.deletePicklist(picklistName);
});

test.describe('Filters in Data Set Manager', () => {
	test('Can create a selection filter', async ({filtersPage, page}) => {
		await test.step('Navigate to the Filters tab', async () => {
			await filtersPage.goto({
				dataSetLabel,
			});
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
		await test.step('Navigate to the Filters tab', async () => {
			await filtersPage.goto({
				dataSetLabel,
			});
		});

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
});

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false}),
	loginTest(),
	picklistApiHelpersTest
);

fragmentTest.describe('Filters in Data Set fragment', () => {
	fragmentTest(
		'Selection filter is displayed in fragment, and applied to data @LPD-10754',
		async ({
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			layout,
			picklistApiHelpers,
		}) => {
			const filterLabel = getRandomString();
			const picklistBooleanOption = 'Boolean';
			const picklistDefaultOption = 'Default';

			await fragmentTest.step('Populate a picklist', async () => {
				await picklistApiHelpers.editPicklist({
					key: picklistBooleanOption.toLocaleLowerCase(),
					name: picklistName,
					value: picklistBooleanOption,
				});

				await picklistApiHelpers.editPicklist({
					key: picklistDefaultOption.toLocaleLowerCase(),
					name: picklistName,
					value: picklistDefaultOption,
				});
			});

			await fragmentTest.step(
				'Add a field, so FDS has something to show',
				async () => {
					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Renderer'},
						name: 'renderer',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					});

					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Sortable'},
						name: 'sortable',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
						renderer: 'boolean',
					});
				}
			);

			await fragmentTest.step(
				'Create a new selection filter',
				async () => {
					const picklist =
						await picklistApiHelpers.getPicklist(picklistName);

					await dataSetManagerApiHelpers.createDataSetSelectionFilter(
						{
							fieldName: 'renderer',
							label_i18n: {en_US: filterLabel},
							r_fdsViewFDSDynamicFilterRelationship_c_fdsViewERC:
								dataSetERC,
							source: picklist.externalReferenceCode,
							sourceType: 'PICKLIST',
						}
					);
				}
			);

			await fragmentTest.step('Configure Data Set fragment', async () => {
				await fdsFragmentPage.configureDataSetFragment({
					dataSetLabel,
					layout,
				});
			});

			await fragmentTest.step(
				'Check current items in the Frontend Data Set',
				async () => {
					await expect(
						fdsFragmentPage.page.getByText(
							'Showing 1 to 2 of 2 entries.'
						)
					).toBeVisible();
				}
			);

			await fragmentTest.step(
				'Filters are available in the fragment',
				async () => {
					await expect(
						fdsFragmentPage.page.getByRole('button', {
							name: 'Filter',
						})
					).toBeVisible();
				}
			);

			await fragmentTest.step('Open filters component', async () => {
				await fdsFragmentPage.page
					.getByRole('button', {name: 'Filter'})
					.click();
			});

			await fragmentTest.step('Select filter', async () => {
				await expect(
					fdsFragmentPage.page.getByRole('menuitem', {
						name: filterLabel,
					})
				).toBeVisible();
				await fdsFragmentPage.page
					.getByRole('menuitem', {name: filterLabel})
					.click();
				await expect(
					fdsFragmentPage.page.getByRole('radio', {
						name: picklistDefaultOption,
					})
				).toBeVisible();
				await expect(
					fdsFragmentPage.page.getByRole('radio', {
						name: picklistBooleanOption,
					})
				).toBeVisible();

				await fdsFragmentPage.page
					.getByRole('radio', {name: picklistBooleanOption})
					.check();
				await fdsFragmentPage.page
					.getByRole('button', {name: 'Add filter'})
					.click();

				// Close filter

				await fdsFragmentPage.page.keyboard.press('Escape');
			});

			await fragmentTest.step('Check that the filter works', async () => {
				await fdsFragmentPage.page
					.locator('.filter-resume')
					.waitFor({state: 'visible'});

				await expect(
					fdsFragmentPage.page.getByRole('button', {
						name: `${filterLabel}: ${picklistBooleanOption}`,
					})
				).toBeVisible();

				await expect(
					fdsFragmentPage.page
						.locator('.dnd-tbody > div')
						.first()
						.locator('.dnd-td')
				).toHaveText(['boolean', 'No', '']);

				await expect(
					fdsFragmentPage.page.getByText(
						'Showing 1 to 1 of 1 entries.'
					)
				).toBeVisible();
			});
		}
	);
});
