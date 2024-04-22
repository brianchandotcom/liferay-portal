/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {filtersPageTest} from './fixtures/filtersPageTest';
import {picklistApiHelpersTest} from './fixtures/picklistApiHelpersTest';

const SELECTION_FILTER_NAME = 'Selection filter';
const PICKLIST_VALUE_KEY = 'sampleValue';
const PICKLIST_VALUE_NAME = 'Sample Value';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-10754': true,
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	filtersPageTest,
	loginTest(),
	dataSetManagerSetupTest,
	picklistApiHelpersTest
);

let filtersDataSetERC: string;
let filtersDataSetLabel: string;
let filtersDataSetViewERC: string;
let filtersDataSetViewLabel: string;
let picklistName: string;

test.beforeEach(async ({dataSetManagerApiHelpers, picklistApiHelpers}) => {
	filtersDataSetERC = getRandomString();
	filtersDataSetLabel = getRandomString();
	filtersDataSetViewERC = getRandomString();
	filtersDataSetViewLabel = getRandomString();
	picklistName = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: filtersDataSetERC,
		label: filtersDataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: filtersDataSetViewERC,
		label: filtersDataSetViewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: filtersDataSetERC,
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

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: filtersDataSetERC});
});

test.describe('Filters in the Data Set Manager', () => {
	test('Can create a selection filter', async ({filtersPage, page}) => {
		await test.step('Navigate to the Filters tab', async () => {
			await filtersPage.goto({
				dataSetLabel: filtersDataSetLabel,
				viewLabel: filtersDataSetViewLabel,
			});
		});

		await test.step('Create a selection filter', async () => {
			await filtersPage.createSelectionFilter({
				filterBy: 'externalReferenceCode',
				filterMode: 'Include',
				name: SELECTION_FILTER_NAME,
				picklist: picklistName,
				preselectedValues: [PICKLIST_VALUE_NAME],
				selectionType: 'Single',
				source: 'Object Picklist',
			});
		});

		await test.step('Check that the selection filter is in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: SELECTION_FILTER_NAME,
				})
			).toBeVisible();
		});
	});
});
