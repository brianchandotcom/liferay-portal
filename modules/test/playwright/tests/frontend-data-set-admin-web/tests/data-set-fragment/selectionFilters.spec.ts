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
import {picklistApiHelpersTest} from '../../fixtures/picklistApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

const picklistBooleanOption = 'Boolean';
const picklistDefaultOption = 'Default';

const apiHeadlessName = 'FieldType';
const apiHeadlessURL = `c/${apiHeadlessName.toLocaleLowerCase()}s`;
let dataSetERC: string;
let dataSetLabel: string;
let objectDefinition: any;
let picklistName: string;

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest,
	picklistApiHelpersTest
);

test.beforeEach(
	async ({apiHelpers, dataSetManagerApiHelpers, picklistApiHelpers}) => {
		dataSetERC = getRandomString();
		dataSetLabel = getRandomString();
		picklistName = getRandomString();

		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});

		await test.step('Create and populate a picklist', async () => {
			await picklistApiHelpers.createPicklist({
				name: picklistName,
			});

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

		await test.step('Create API Headless application and populate with filter values', async () => {
			objectDefinition =
				await apiHelpers.objectAdmin.postObjectDefinition({
					enableLocalization: true,
					label: {
						en_US: 'Field Type',
					},
					modifiable: true,
					name: apiHeadlessName,
					objectFields: [
						{
							DBType: 'String',
							businessType: 'Text',
							indexed: true,
							indexedAsKeyword: true,
							label: {
								en_US: 'type',
							},
							localized: true,
							name: 'type',
							required: false,
							state: false,
						},
					],
					pluralLabel: {en_US: `${apiHeadlessName}s`},
					scope: 'company',
				});

			await apiHelpers.objectAdmin.postObjectDefinitionPublish(
				objectDefinition.id
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{type: 'array'},
				apiHeadlessURL
			);
			await apiHelpers.objectEntry.postObjectEntry(
				{type: 'boolean'},
				apiHeadlessURL
			);
			await apiHelpers.objectEntry.postObjectEntry(
				{type: 'integer'},
				apiHeadlessURL
			);
			await apiHelpers.objectEntry.postObjectEntry(
				{type: 'object'},
				apiHeadlessURL
			);
			await apiHelpers.objectEntry.postObjectEntry(
				{type: 'string'},
				apiHeadlessURL
			);
		});
	}
);

test.afterEach(
	async ({apiHelpers, dataSetManagerApiHelpers, picklistApiHelpers}) => {
		await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});

		await picklistApiHelpers.deletePicklist(picklistName);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	}
);

test.describe('Selection filters in Data Set fragment', () => {
	test('Picklist filter is displayed in fragment, and applied to data @LPD-10754', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
		picklistApiHelpers,
	}) => {
		const filterLabel = getRandomString();

		await test.step('Add a field, so FDS has something to show', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Renderer'},
				name: 'renderer',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Sortable'},
				name: 'sortable',
				renderer: 'boolean',
			});
		});

		await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('There are no filters in the Frontend Data Set', async() => {
			await expect(
				fdsFragmentPage.page.getByRole('button', {
					name: 'Filter',
				})
			).not.toBeVisible();
		});

		await test.step('Create a new selection filter', async () => {
			const picklist = await picklistApiHelpers.getPicklist(picklistName);

			await dataSetManagerApiHelpers.createDataSetSelectionFilter({
				dataSetERC,
				fieldName: 'renderer',
				label_i18n: {en_US: filterLabel},
				source: picklist.externalReferenceCode,
				sourceType: 'PICKLIST',
			});
		});

		await test.step('Check current items in the Frontend Data Set', async () => {
			await page.reload();
			await fdsFragmentPage.fdsPaginationResults.scrollIntoViewIfNeeded();

			await expect(
				fdsFragmentPage.fdsPaginationResults.getByText(
					'Showing 1 to 2 of 2 entries.'
				)
			).toBeVisible();
		});

		await test.step('Filters are available in the fragment', async () => {
			await expect(
				fdsFragmentPage.page.getByRole('button', {
					name: 'Filter',
				})
			).toBeVisible();
		});

		await test.step('Open filters component', async () => {
			await fdsFragmentPage.page
				.getByRole('button', {name: 'Filter'})
				.click();
		});

		await test.step('Select filter', async () => {
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

		await test.step('Check that the filter works', async () => {
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
				fdsFragmentPage.page.getByText('Showing 1 to 1 of 1 entries.')
			).toBeVisible();
		});
	});

	test('API Headless filter is displayed in fragment, and applied to data @LPD-10754', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
	}) => {
		const filterLabel = getRandomString();

		await test.step('Add fields, so FDS has something to show', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Id'},
				name: 'id',
				type: 'integer',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Type'},
				name: 'type',
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Sortable'},
				name: 'sortable',
				type: 'boolean',
			});
		});

		await test.step('Create a new API Headless selection filter', async () => {
			await dataSetManagerApiHelpers.createDataSetSelectionFilter({
				dataSetERC,
				fieldName: 'type',
				itemKey: 'type',
				itemLabel: 'type',
				label_i18n: {en_US: filterLabel},
				multiple: true,
				source: `/o/${apiHeadlessURL}`,
				sourceType: 'API_HEADLESS',
			});
		});

		await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check current items in the Frontend Data Set', async () => {
			await fdsFragmentPage.fdsPaginationResults.scrollIntoViewIfNeeded();

			await expect(
				fdsFragmentPage.fdsPaginationResults.getByText(
					'Showing 1 to 3 of 3 entries.'
				)
			).toBeVisible();
		});

		await test.step('Filters are available in the fragment', async () => {
			await expect(
				fdsFragmentPage.page.getByRole('button', {
					name: 'Filter',
				})
			).toBeVisible();
		});

		await test.step('Open filters component', async () => {
			await fdsFragmentPage.page
				.getByRole('button', {exact: true, name: 'Filter'})
				.click();
		});

		await test.step('Select filter', async () => {
			await expect(
				fdsFragmentPage.page.getByRole('menuitem', {
					name: filterLabel,
				})
			).toBeVisible();
			await fdsFragmentPage.page
				.getByRole('menuitem', {name: filterLabel})
				.click();
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {
					name: 'array',
				})
			).toBeVisible();
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {
					name: 'boolean',
				})
			).toBeVisible();
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {
					name: 'integer',
				})
			).toBeVisible();
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {
					name: 'object',
				})
			).toBeVisible();
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {
					name: 'string',
				})
			).toBeVisible();

			await fdsFragmentPage.page
				.getByRole('checkbox', {name: 'integer'})
				.check();
			await fdsFragmentPage.page
				.getByRole('button', {name: 'Add filter'})
				.click();

			// Close filter

			await fdsFragmentPage.page.keyboard.press('Escape');
		});

		await test.step('Check that the filter works', async () => {
			await fdsFragmentPage.page
				.locator('.filter-resume')
				.waitFor({state: 'visible'});

			await expect(
				fdsFragmentPage.page.getByRole('button', {
					name: `${filterLabel}: integer`,
				})
			).toBeVisible();

			await expect(
				fdsFragmentPage.page
					.locator('.dnd-tr')
					.filter({
						has: fdsFragmentPage.page
							.getByText('integer', {exact: true})
							.first(),
					})
					.locator('.dnd-td')
					.nth(1)
			).toHaveText(['integer']);

			await expect(
				fdsFragmentPage.page.getByText('Showing 1 to 1 of 1 entries.')
			).toBeVisible();
		});

		await test.step('Open filters component', async () => {
			await fdsFragmentPage.page
				.getByRole('button', {exact: true, name: 'Filter'})
				.click();
		});

		await test.step('Select filter', async () => {
			await expect(
				fdsFragmentPage.page.getByRole('checkbox', {name: 'boolean'})
			).toBeVisible();

			await fdsFragmentPage.page
				.getByRole('checkbox', {name: 'boolean'})
				.check();
			await fdsFragmentPage.page
				.getByRole('button', {name: 'Add filter'})
				.click();

			// Close filter

			await fdsFragmentPage.page.keyboard.press('Escape');
		});

		await test.step('Check that the filter works', async () => {
			await fdsFragmentPage.page
				.locator('.filter-resume')
				.waitFor({state: 'visible'});

			await expect(
				fdsFragmentPage.page.getByRole('button', {
					name: `${filterLabel}: integer, boolean`,
				})
			).toBeVisible();

			await expect(
				fdsFragmentPage.page
					.locator('.dnd-tr')
					.filter({
						has: fdsFragmentPage.page
							.getByText('boolean', {exact: true})
							.first(),
					})
					.locator('.dnd-td')
					.nth(1)
			).toHaveText(['boolean']);

			await expect(
				fdsFragmentPage.page.getByText('Showing 1 to 2 of 2 entries.')
			).toBeVisible();
		});
	});
});
