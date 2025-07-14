/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {createReadStream} from 'fs';
import path from 'path';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {EFDSVisualizationMode, waitForFDS} from '../../../utils/waitFor';
import {itemSelectorSamplePageTest} from './fixtures/itemSelectorSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	itemSelectorSamplePageTest,
	isolatedLayoutTest({publish: false}),
	loginTest()
);

let imageFile: any;
let jsonFile: any;

test.beforeEach(async ({apiHelpers, itemSelectorSamplePage, layout, page}) => {
	await itemSelectorSamplePage.configureItemSelector({layout});

	await expect(page.getByText('Item Selector Samples')).toBeVisible();

	await test.step('Upload sample documents', async () => {
		imageFile = await apiHelpers.headlessDelivery.postDocument(
			layout.groupId,
			createReadStream(path.join(__dirname, '/dependencies/moon.png')),
			{
				description: getRandomString(),
				documentFolderId: 0,
				fileName: getRandomString(),
				title: 'Sample image',
			}
		);

		jsonFile = await apiHelpers.headlessDelivery.postDocument(
			layout.groupId,
			createReadStream(path.join(__dirname, '/dependencies/file.json')),
			{
				description: getRandomString(),
				documentFolderId: 0,
				fileName: getRandomString(),
				title: 'JSON File',
			}
		);
	});
});

test.afterEach(async ({apiHelpers}) => {
	await apiHelpers.headlessDelivery.deleteDocument(imageFile.id);
	await apiHelpers.headlessDelivery.deleteDocument(jsonFile.id);
});

test('Item Selector Modal with single selection', async ({
	itemSelectorSamplePage,
	page,
}) => {
	await test.step('Check that an Item Selector Modal appears in the page', async () => {
		await expect(page.getByText('Item Selector Modal')).toBeVisible();
	});

	await test.step('Click in the Select File button opens the Item Selector Modal with a FDS component', async () => {
		await itemSelectorSamplePage.selectFileButton.click();

		await expect(
			itemSelectorSamplePage.selectFileModalHeader
		).toBeVisible();

		waitForFDS({page, visualizationMode: EFDSVisualizationMode.CARDS});
	});

	await test.step('Check that a single item can be selected in the Cards visualization mode', async () => {
		await itemSelectorSamplePage.page
			.locator('.custom-radio')
			.first()
			.click();

		await expect(
			itemSelectorSamplePage.page.getByText(
				`${imageFile.fileName} Selected`
			)
		).toBeVisible();

		await itemSelectorSamplePage.page
			.locator('.custom-radio')
			.last()
			.click();

		await expect(
			itemSelectorSamplePage.page.getByText(
				`${jsonFile.fileName} Selected`
			)
		).toBeVisible();
	});

	await test.step('Check that it is possible to select an item in the Table visualization mode', async () => {
		await itemSelectorSamplePage.changeVisualizationMode({
			page,
			visualizationMode: EFDSVisualizationMode.TABLE,
		});

		itemSelectorSamplePage.selectByRowAndRole({row: 0});

		await expect(
			itemSelectorSamplePage.page.getByText(
				`${imageFile.fileName} Selected`
			)
		).toBeVisible();

		itemSelectorSamplePage.selectByRowAndRole({row: 1});

		await expect(
			itemSelectorSamplePage.page.getByText(
				`${jsonFile.fileName} Selected`
			)
		).toBeVisible();
	});
});
