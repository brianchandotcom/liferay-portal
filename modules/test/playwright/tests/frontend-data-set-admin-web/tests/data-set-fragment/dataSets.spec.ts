/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';

// Structured Content utilities

import getBasicWebContentStructureId from '../../../../utils/structured-content/getBasicWebContentStructureId';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

const dataSetERCs = [];
let article;
let dataSetERC;
let dataSetLabel;
let siteId;
let structuredContentId;
let structuredContentTitle;

const structuredContentDataSetConfig = {
	erc: getRandomString(),
	label: getRandomString(),
	name: getRandomString(),
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/structured-contents',
	restSchema: 'StructuredContent',
};

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	dataSetERCs.push(dataSetERC);

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({apiHelpers, dataSetManagerApiHelpers}) => {
	for (const DATA_SET_ERC of dataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc: DATA_SET_ERC,
		});
	}

	dataSetERCs.length = 0;

	await test.step('Move article to trash', async () => {
		await apiHelpers.jsonWebServicesJournal.moveArticleToTrash(
			siteId,
			article.articleId
		);
	});
});

test('Data Set can be added to the fragment', async ({
	dataSetManagerApiHelpers,
	fdsFragmentPage,
	layout,
	page,
}) => {
	await test.step('Add fields, so data is displayed', async () => {
		await dataSetManagerApiHelpers.createDataSetField({
			dataSetERC,
			label_i18n: {
				en_US: 'ID',
			},
			name: 'id',
			sortable: true,
			type: 'string',
		});

		await dataSetManagerApiHelpers.createDataSetField({
			dataSetERC,
			label_i18n: {en_US: 'Name'},
			name: 'name',
			sortable: true,
			type: 'string',
		});
	});

	await test.step('Configure Data Set fragment', async () => {
		await fdsFragmentPage.configureDataSetFragment({
			dataSetLabel,
			layout,
		});
	});

	await test.step('Assert that the Data Set is available on the page', async () => {
		await fdsFragmentPage.fdsTableWrapper.waitFor({
			state: 'visible',
		});

		await expect(await fdsFragmentPage.fdsTableWrapper).toBeInViewport();

		expect(
			await page
				.locator('.dnd-thead > div')
				.first()
				.locator('.dnd-th')
				.allInnerTexts()
		).toEqual(['ID', 'Name', '']);
	});
});

test('Data Set selection modal shows a "No results found" message when there are no Data Sets created', async ({
	dataSetManagerApiHelpers,
	fdsFragmentPage,
	layout,
}) => {
	test.step('Remove Data Set', async () => {
		await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
	});

	await test.step('Configure Data Set fragment', async () => {
		await fdsFragmentPage.configureEmptyDataSetFragment({
			layout,
		});
	});

	test.step('Assert that there are no Data Sets available to select', async () => {
		await expect(
			fdsFragmentPage.page
				.frameLocator('iframe[title="Select"]')
				.locator('.c-empty-state-title')
		).toContainText('No Results Found');
	});
});

test('Data Set can use different sources of data: StructuredContentSchema, UserSchema, TaxonomyVocabularySchema', async ({
	apiHelpers,
	dataSetManagerApiHelpers,
	fdsFragmentPage,
	layout,
	page,
}) => {
	const structuredContentDescription = getRandomString();
	structuredContentTitle = 'Sample Structured Content title';
	structuredContentId = await getBasicWebContentStructureId(apiHelpers);

	siteId = await page.evaluate(() => {
		return Liferay.ThemeDisplay.getSiteGroupId();
	});

	await test.step('Create a Structured Content Schema Data Set and add fields', async () => {
		dataSetERCs.push(structuredContentDataSetConfig.erc);

		await dataSetManagerApiHelpers.createDataSet({
			erc: structuredContentDataSetConfig.erc,
			label: structuredContentDataSetConfig.label,
			restApplication: structuredContentDataSetConfig.restApplication,
			restEndpoint: structuredContentDataSetConfig.restEndpoint,
			restSchema: structuredContentDataSetConfig.restSchema,
		});

		await dataSetManagerApiHelpers.createDataSetField({
			dataSetERC: structuredContentDataSetConfig.erc,
			label_i18n: {
				en_US: 'Title',
			},
			name: 'title',
			sortable: false,
			type: 'string',
		});

		await dataSetManagerApiHelpers.createDataSetField({
			dataSetERC: structuredContentDataSetConfig.erc,
			label_i18n: {en_US: 'Description'},
			name: 'description',
			sortable: false,
			type: 'string',
		});

		article = await apiHelpers.jsonWebServicesJournal.addWebContent({
			ddmStructureId: structuredContentId,
			descriptionMap: {en_US: structuredContentDescription},
			groupId: siteId,
			titleMap: {en_US: structuredContentTitle},
		});
	});

	await test.step('Configure Structured Content Schema Data Set fragment', async () => {
		await fdsFragmentPage.configureDataSetFragment({
			dataSetLabel: structuredContentDataSetConfig.label,
			layout,
		});
	});

	await test.step('Assert that the Data Set is available on the page', async () => {
		await fdsFragmentPage.fdsTableWrapper.waitFor({
			state: 'visible',
		});

		await expect(await fdsFragmentPage.fdsTableWrapper).toBeInViewport();

		expect(
			await page
				.locator('.dnd-thead > div')
				.first()
				.locator('.dnd-th')
				.allInnerTexts()
		).toEqual(['Title', 'Description', '']);

		expect(
			await page
				.locator('.dnd-tbody > .dnd-tr')
				.first()
				.locator('.dnd-td')
				.allInnerTexts()
		).toEqual([structuredContentTitle, structuredContentDescription, '']);
	});
});
