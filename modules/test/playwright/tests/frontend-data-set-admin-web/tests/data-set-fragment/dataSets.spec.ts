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

const adminUserDataSetConfig = {
	erc: getRandomString(),
	label: getRandomString(),
	restApplication: '/headless-admin-user/v1.0',
	restEndpoint: '/v1.0/roles',
	restSchema: 'Role',
};

const structuredContentDataSetConfig = {
	erc: getRandomString(),
	label: getRandomString(),
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/structured-contents',
	restSchema: 'StructuredContent',
};

const taxonomyVocabularyDataSetConfig = {
	erc: getRandomString(),
	label: getRandomString(),
	restApplication: '/headless-admin-taxonomy/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/taxonomy-vocabularies',
	restSchema: 'TaxonomyVocabulary',
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

	if (article) {
		await test.step('Move article to trash', async () => {
			await apiHelpers.jsonWebServicesJournal.moveArticleToTrash(
				siteId,
				article.articleId
			);
		});
	}
});

test(
	'Data Set can be added to the fragment and the fragment can be removed',
	{
		tag: '@LPS-172403',
	},
	async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
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

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['ID', 'Name', '']);
		});

		await test.step('Remove Data Set Fragment from the page', async () => {
			await fdsFragmentPage.editPage({layout});

			await fdsFragmentPage.fdsTableWrapper.click();

			const dataSetFragmentOptionsButton = page
				.locator('.page-editor__topper__item.tbar-item')
				.getByLabel('Options');
			await dataSetFragmentOptionsButton.click();

			const dataSetFragmentOptionsDropdownId =
				await dataSetFragmentOptionsButton.evaluate((node) =>
					node.getAttribute('aria-controls')
				);
			await page
				.locator(`#${dataSetFragmentOptionsDropdownId}`)
				.waitFor();

			await page
				.locator(`#${dataSetFragmentOptionsDropdownId}`)
				.getByRole('menuitem', {name: 'Delete'})
				.click();
		});

		await test.step('Assert that the Data Set Fragment is not available on the page', async () => {
			await expect(
				page.getByText('Place fragments or widgets here.')
			).toBeInViewport();

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).not.toBeInViewport();
		});
	}
);

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

test(
	'Data Set can use different sources of data: StructuredContentSchema, UserSchema, TaxonomyVocabularySchema',
	{
		tag: ['@LPS-172403', '@LPS-190724'],
	},
	async ({
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

		await test.step('Create an Admin User Schema (Roles) Data Set and add fields', async () => {
			dataSetERCs.push(adminUserDataSetConfig.erc);

			await dataSetManagerApiHelpers.createDataSet({
				erc: adminUserDataSetConfig.erc,
				label: adminUserDataSetConfig.label,
				restApplication: adminUserDataSetConfig.restApplication,
				restEndpoint: adminUserDataSetConfig.restEndpoint,
				restSchema: adminUserDataSetConfig.restSchema,
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC: adminUserDataSetConfig.erc,
				label_i18n: {
					en_US: 'Role Type',
				},
				name: 'roleType',
				sortable: false,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC: adminUserDataSetConfig.erc,
				label_i18n: {en_US: 'Name'},
				name: 'name',
				sortable: false,
				type: 'string',
			});
		});

		await test.step('Create a Taxonomy Vocabulary Data Set and add fields', async () => {
			dataSetERCs.push(taxonomyVocabularyDataSetConfig.erc);

			await dataSetManagerApiHelpers.createDataSet({
				erc: taxonomyVocabularyDataSetConfig.erc,
				label: taxonomyVocabularyDataSetConfig.label,
				restApplication:
					taxonomyVocabularyDataSetConfig.restApplication,
				restEndpoint: taxonomyVocabularyDataSetConfig.restEndpoint,
				restSchema: taxonomyVocabularyDataSetConfig.restSchema,
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC: taxonomyVocabularyDataSetConfig.erc,
				label_i18n: {
					en_US: 'Vocabulary Name',
				},
				name: 'name',
				sortable: false,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC: taxonomyVocabularyDataSetConfig.erc,
				label_i18n: {en_US: 'Number of Categories'},
				name: 'numberOfTaxonomyCategories',
				sortable: false,
				type: 'integer',
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

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).toBeInViewport();

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
			).toEqual([
				structuredContentTitle,
				structuredContentDescription,
				'',
			]);
		});

		await test.step('Confirm that we can change the Data Set and display the Roles Data Set', async () => {
			await fdsFragmentPage.editPage({layout});
			await fdsFragmentPage.fdsTableWrapper.click();

			await page
				.getByRole('button', {name: 'Select Data Set View'})
				.click();

			await page
				.getByRole('menuitem', {name: 'Select Data Set View...'})
				.click();

			await page.getByRole('dialog').isVisible();

			await page.getByRole('heading', {name: 'Select'}).isVisible();

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('.fds-admin-item-selector')
				.waitFor({state: 'visible'});

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: adminUserDataSetConfig.label})
				.first()
				.click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();

			await fdsFragmentPage.publishPage();

			await fdsFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that the User Schema (Roles) Data Set is available on the page', async () => {
			await fdsFragmentPage.fdsTableWrapper.waitFor({
				state: 'visible',
			});

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['Role Type', 'Name', '']);

			expect(
				await page
					.locator('.dnd-tbody > .dnd-tr')
					.first()
					.locator('.dnd-td')
					.allInnerTexts()
			).toEqual(['organization', 'Account Manager', '']);
		});

		await test.step('Confirm that we can change the Data Set and display the Taxonomy Vocabulary Data Set', async () => {
			await fdsFragmentPage.editPage({layout});
			await fdsFragmentPage.fdsTableWrapper.click();

			await page
				.getByRole('button', {name: 'Select Data Set View'})
				.click();

			await page
				.getByRole('menuitem', {name: 'Select Data Set View...'})
				.click();

			await page.getByRole('dialog').isVisible();

			await page.getByRole('heading', {name: 'Select'}).isVisible();

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('.fds-admin-item-selector')
				.waitFor({state: 'visible'});

			await page
				.frameLocator('iframe[title="Select"]')
				.locator('li')
				.filter({hasText: taxonomyVocabularyDataSetConfig.label})
				.first()
				.click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Save'})
				.click();

			await fdsFragmentPage.publishPage();

			await fdsFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that the Taxonomy Vocabulary Data Set is available on the page', async () => {
			await fdsFragmentPage.fdsTableWrapper.waitFor({
				state: 'visible',
			});

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['Vocabulary Name', 'Number of Categories', '']);

			expect(
				await page
					.locator('.dnd-tbody > .dnd-tr')
					.first()
					.locator('.dnd-td')
					.allInnerTexts()
			).toEqual(['Topic', '0', '']);
		});
	}
);
