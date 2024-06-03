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
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest(),
	dataSetManagerSetupTest
);

const DATASET_LABEL = getRandomString();

const tableSectionsDataSetConfig = {
	name: DATASET_LABEL,
	restApplication: '/data-set-manager/table-sections',
	restEndpoint: '/',
	restSchema: 'FDSField',
};

async function assertTableActionLabels(page) {
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
}

async function assertTableCellContent(page, dataSetConfig) {
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
}

async function assertTableColumnLabels(page) {
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

		expect(tableColumnLabels).toEqual(expectedLabels);
	});
}

test('Create data set via UI', async ({dataSetsPage, page}) => {
	await test.step('Create Data Set', async () => {
		await dataSetsPage.goto();
		await dataSetsPage.createDataSet(tableSectionsDataSetConfig);
	});

	await assertTableColumnLabels(page);

	await assertTableCellContent(page, tableSectionsDataSetConfig);

	await assertTableActionLabels(page);

	await test.step('Delete Data Set', async () => {
		await dataSetsPage.deleteDataSet(tableSectionsDataSetConfig.name);
	});
});

test('Create data set via API', async ({
	dataSetManagerApiHelpers,
	dataSetsPage,
	page,
}) => {
	const DEFAULT_DATA_SET_ERC = getRandomString();

	await test.step('Create Data Set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			...tableSectionsDataSetConfig,
			erc: DEFAULT_DATA_SET_ERC,
			label: tableSectionsDataSetConfig.name,
		});
	});

	await test.step('Navigate to Data Sets page', async () => {
		await dataSetsPage.goto();
	});

	await assertTableColumnLabels(page);

	await assertTableCellContent(page, tableSectionsDataSetConfig);

	await assertTableActionLabels(page);

	await test.step('Delete Data Set', async () => {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc: DEFAULT_DATA_SET_ERC,
		});
	});
});
