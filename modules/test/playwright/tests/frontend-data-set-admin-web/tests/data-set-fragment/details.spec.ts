/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accountSettingsPagesTest} from '../../../../fixtures/accountSettingsPagesTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

export const test = mergeTests(
	accountSettingsPagesTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-25230': true,
		'LPS-164563': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

let dataSetERC: string;
let dataSetLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Parameters in Data Set Fragment', () => {
	test('Check that the sort parameter is applied @LPD-25241', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
	}) => {
		await test.step('Add fields', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Name'},
				name: 'name',
				sortable: true,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'ID'},
				name: 'id',
				sortable: true,
				type: 'string',
			});
		});

		await test.step('Add parameters', async () => {
			await dataSetManagerApiHelpers.updateDataSet({
				erc: dataSetERC,
				queryString: 'sort=name:desc',
			});
		});

		await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the sorting is applied', async () => {
			const firstNameText = await fdsFragmentPage.fdsTableWrapper
				.locator('.dnd-tbody .dnd-tr:first-child .dnd-td:first-child')
				.textContent();

			const lastNameText = await fdsFragmentPage.fdsTableWrapper
				.locator('.dnd-tbody .dnd-tr:last-child .dnd-td:first-child')
				.textContent();

			expect(firstNameText > lastNameText).toBeTruthy();
		});
	});

	test('Check that the filter parameter is applied @LPD-25241', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
	}) => {
		await test.step('Add fields', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Name'},
				name: 'name',
				sortable: true,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'ID'},
				name: 'id',
				sortable: true,
				type: 'string',
			});
		});

		await test.step('Add parameters', async () => {
			await dataSetManagerApiHelpers.updateDataSet({
				erc: dataSetERC,
				queryString: "filter=name eq 'name'",
			});
		});

		await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check that the filter is applied', async () => {
			const tableNameCellTexts = await fdsFragmentPage.fdsTableWrapper
				.locator(`.dnd-tbody > .dnd-tr > .dnd-td:nth-child(1)`)
				.allInnerTexts();

			expect(
				tableNameCellTexts.every((value) => value === 'name')
			).toBeTruthy();
		});
	});
});
