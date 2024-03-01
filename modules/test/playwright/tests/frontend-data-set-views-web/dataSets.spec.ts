/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {DEFAULT_LABEL} from './utils/constants';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest()
);

const dataSetConfig = {
	name: DEFAULT_LABEL.DATA_SET,
	restApplication: '/data-set-manager/fields',
	restEndpoint: '/',
	restSchema: 'FDSField',
};

const DEFAULT_DATA_SET_ERC = 'sampleDataSetERC';

test.describe('Data Set Table', () => {
	test('Data Set created via UI', async ({dataSetsPage, page}) => {
		await test.step('Create Data Set', async () => {
			await dataSetsPage.goto();
			await dataSetsPage.createDataSet(dataSetConfig);
		});

		await test.step('Assert table column labels', async () => {
			await page.locator('.dnd-table > .dnd-thead > .dnd-tr').waitFor();

			const tableColumnLabels = await page
				.locator('.dnd-thead > .dnd-tr')
				.first()
				.locator('.dnd-th')
				.allInnerTexts();

			const expectedLabels = [
				'Name',
				'REST Application',
				'REST Schema',
				'REST Endpoint',
				'Modified Date',
				'',
			];

			await expect(tableColumnLabels).toEqual(expectedLabels);
		});

		await test.step('Assert table cell content', async () => {
			await page
				.locator('.dnd-table > .dnd-tbody > .dnd-tr')
				.first()
				.waitFor();

			const tableRowContent = await page
				.locator('.dnd-tbody > .dnd-tr')
				.first()
				.locator('.dnd-td');

			const expectedRowContent = [
				dataSetConfig.name,
				dataSetConfig.restApplication,
				dataSetConfig.restSchema,
				dataSetConfig.restEndpoint,
			];

			await expect(tableRowContent).toContainText(expectedRowContent);
		});

		await test.step('Assert table action labels', async () => {
			await page.locator('.dnd-td.item-actions').first().waitFor();

			await page
				.locator('.dnd-td.item-actions')
				.first()
				.locator('.dropdown-toggle')
				.click();

			const tableItemActions = await page
				.locator('.dropdown-menu')
				.filter({has: page.locator('span.pr-2')})
				.first()
				.locator('.dropdown-item')
				.allInnerTexts();

			const expectedLabels = ['Edit', 'Permissions', 'Delete'];

			await expect(tableItemActions).toEqual(expectedLabels);
		});

		await test.step('Delete Data Set', async () => {
			await dataSetsPage.deleteDataSet(dataSetConfig.name);
		});
	});

	test('Data Set created via API', async ({
		dataSetManagerApiHelpers,
		dataSetsPage,
		page,
	}) => {
		await test.step('Create Data Set', async () => {
			await dataSetManagerApiHelpers.createDataSet({
				...dataSetConfig,
				erc: DEFAULT_DATA_SET_ERC,
			});
		});

		await test.step('Navigate to Data Sets page', async () => {
			await dataSetsPage.goto();
		});

		await test.step('Assert table column labels', async () => {
			await page.locator('.dnd-table > .dnd-thead > .dnd-tr').waitFor();

			const tableColumnLabels = await page
				.locator('.dnd-thead > .dnd-tr')
				.first()
				.locator('.dnd-th')
				.allInnerTexts();

			const expectedLabels = [
				'Name',
				'REST Application',
				'REST Schema',
				'REST Endpoint',
				'Modified Date',
				'',
			];

			await expect(tableColumnLabels).toEqual(expectedLabels);
		});

		await test.step('Assert table cell content', async () => {
			await page
				.locator('.dnd-table > .dnd-tbody > .dnd-tr')
				.first()
				.waitFor();

			const tableRowContent = await page
				.locator('.dnd-tbody > .dnd-tr')
				.first()
				.locator('.dnd-td');

			const expectedRowContent = [
				dataSetConfig.name,
				dataSetConfig.restApplication,
				dataSetConfig.restSchema,
				dataSetConfig.restEndpoint,
			];

			await expect(tableRowContent).toContainText(expectedRowContent);
		});

		await test.step('Assert table action labels', async () => {
			await page.locator('.dnd-td.item-actions').first().waitFor();

			await page
				.locator('.dnd-td.item-actions')
				.first()
				.locator('.dropdown-toggle')
				.click();

			const tableItemActions = await page
				.locator('.dropdown-menu')
				.filter({has: page.locator('span.pr-2')})
				.first()
				.locator('.dropdown-item')
				.allInnerTexts();

			const expectedLabels = ['Edit', 'Permissions', 'Delete'];

			await expect(tableItemActions).toEqual(expectedLabels);
		});

		await test.step('Delete Data Set', async () => {
			await dataSetManagerApiHelpers.deleteDataSet({
				erc: DEFAULT_DATA_SET_ERC,
			});
		});
	});
});
