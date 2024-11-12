/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../../utils/getRandomString';

// Structured Content utilities

import getBasicWebContentStructureId from '../../../../utils/structured-content/getBasicWebContentStructureId';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {dataSetFragmentPageTest} from './fixtures/dataSetFragmentPageTest';

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	dataSetFragmentPageTest
);

const dataSetERCs: string[] = [];
let article: any;
let siteId: string;
let structuredContentId: number;
let structuredContentTitle: string;

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
	'Assing a data set to the "Data Set" fragment, change and delete assignment',
	{
		tag: '@LPS-172403',
	},
	async ({dataSetFragmentPage, dataSetManagerApiHelpers, layout, page}) => {
		const dataSetERC1 = getRandomString();
		const dataSetERC2 = getRandomString();
		const dataSetLabel1 = getRandomString();
		const dataSetLabel2 = getRandomString();

		dataSetERCs.push(dataSetERC1);
		dataSetERCs.push(dataSetERC2);

		await test.step('Create data sets', async () => {
			await dataSetManagerApiHelpers.createDataSet({
				erc: dataSetERC1,
				label: dataSetLabel1,
			});

			await dataSetManagerApiHelpers.createDataSet({
				erc: dataSetERC2,
				label: dataSetLabel2,
			});
		});

		await test.step('Create sample data for data sets', async () => {
			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: dataSetERC1,
				fieldName: 'fieldName',
				label_i18n: {en_US: 'Field Name'},
			});

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: dataSetERC2,
				fieldName: 'id',
				label_i18n: {en_US: 'ID'},
			});

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: dataSetERC2,
				fieldName: 'fieldName',
				label_i18n: {en_US: 'Field Name'},
			});
		});

		await test.step('Go to page configuration, add "Data Set" fragment', async () => {
			await dataSetFragmentPage.addDataSetFragment(layout);
		});

		await test.step('Assign first data set to fragment', async () => {
			await dataSetFragmentPage.selectDataSetButton.click();

			await dataSetFragmentPage.selectDataSet(dataSetLabel1);
		});

		await test.step('Change assigment to second data set', async () => {
			await dataSetFragmentPage.changeDataSetButton.click();

			const selectionListContainer =
				dataSetFragmentPage.selectDataSetModalFrame.locator(
					'.fds-admin-item-selector'
				);

			await expect(selectionListContainer).toBeVisible();

			await expect(
				selectionListContainer
					.locator('.selectable.list-group-item')
					.filter({
						has: page.getByText(dataSetLabel1, {exact: true}),
					})
					.getByRole('radio')
			).toBeChecked();

			await dataSetFragmentPage.selectDataSet(dataSetLabel2);
		});

		await test.step('Assert that the data set is available on the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.tableWrapper).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['ID', 'Field Name', '']);
		});

		await test.step('Unassign data set', async () => {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {
					name: 'Remove Data Set View',
				}),
				trigger: page.getByRole('button', {
					name: 'View Data Set View Options',
				}),
			});

			await expect(dataSetFragmentPage.selectedDataSetInput).toHaveValue(
				''
			);
		});

		await test.step('Remove "Data Set" fragment from the page', async () => {
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

		await test.step('Assert that "Data Set" fragment is not available on the page', async () => {
			await expect(
				page.getByText('Drag and drop fragments or widgets here.')
			).toBeInViewport();

			await expect(
				await dataSetFragmentPage.tableWrapper
			).not.toBeInViewport();
		});
	}
);

test('Data set selection modal shows a "No results found" message when there are no data sets created', async ({
	dataSetFragmentPage,
	layout,
}) => {
	await test.step('Go to page configuration, add "Data Set" fragment', async () => {
		await dataSetFragmentPage.addDataSetFragment(layout);
	});

	await test.step('Open data set selection modal', async () => {
		await dataSetFragmentPage.selectDataSetButton.click();
	});

	await test.step('Assert that there are no Data Sets available to select', async () => {
		await dataSetFragmentPage.selectDataSetModalFrame
			.locator('.fds-admin-item-selector')
			.waitFor({state: 'visible'});

		await expect(
			dataSetFragmentPage.selectDataSetModalFrame.locator(
				'.c-empty-state-title'
			)
		).toContainText('No Results Found');
	});
});

test(
	'"Data Set" fragment can display different sources of data: StructuredContentSchema, UserSchema, TaxonomyVocabularySchema',
	{
		tag: ['@LPS-172403', '@LPS-190724'],
	},
	async ({
		apiHelpers,
		dataSetFragmentPage,
		dataSetManagerApiHelpers,
		layout,
		page,
	}) => {
		const structuredContentDescription = getRandomString();
		structuredContentTitle = 'Sample Structured Content title';
		structuredContentId = await getBasicWebContentStructureId(apiHelpers);

		siteId = await page.evaluate(() => {
			return String(Liferay.ThemeDisplay.getSiteGroupId());
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

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: structuredContentDataSetConfig.erc,
				fieldName: 'title',
				label_i18n: {
					en_US: 'Title',
				},
				sortable: false,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: structuredContentDataSetConfig.erc,
				fieldName: 'description',
				label_i18n: {en_US: 'Description'},
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

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: adminUserDataSetConfig.erc,
				fieldName: 'roleType',
				label_i18n: {
					en_US: 'Role Type',
				},
				sortable: false,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: adminUserDataSetConfig.erc,
				fieldName: 'name',
				label_i18n: {en_US: 'Name'},
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

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: taxonomyVocabularyDataSetConfig.erc,
				fieldName: 'name',
				label_i18n: {
					en_US: 'Vocabulary Name',
				},
				sortable: false,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC: taxonomyVocabularyDataSetConfig.erc,
				fieldName: 'numberOfTaxonomyCategories',
				label_i18n: {en_US: 'Number of Categories'},
				sortable: false,
				type: 'integer',
			});
		});

		await test.step('Configure Structured Content Schema Data Set fragment', async () => {
			await dataSetFragmentPage.configureDataSetFragment({
				dataSetLabel: structuredContentDataSetConfig.label,
				layout,
			});
		});

		await test.step('Assert that the Data Set is available on the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.tableWrapper).toBeInViewport();

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
					.locator('.dnd-td')
					.allInnerTexts()
			).toEqual(
				expect.arrayContaining([
					structuredContentTitle,
					structuredContentDescription,
				])
			);
		});

		await test.step('Confirm that we can change the Data Set and display the Roles Data Set', async () => {
			await dataSetFragmentPage.editPage({layout});

			await dataSetFragmentPage.tableWrapper.click();

			await dataSetFragmentPage.changeDataSetButton.click();

			const selectionListContainer =
				dataSetFragmentPage.selectDataSetModalFrame.locator(
					'.fds-admin-item-selector'
				);

			await expect(selectionListContainer).toBeVisible();

			await dataSetFragmentPage.selectDataSetModalFrame
				.locator('li')
				.filter({hasText: adminUserDataSetConfig.label})
				.first()
				.click();

			await dataSetFragmentPage.selectDataSetModalFrame
				.getByRole('button', {name: 'Save'})
				.click();

			await dataSetFragmentPage.publishPage();

			await dataSetFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that the User Schema (Roles) Data Set is available on the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.tableWrapper).toBeInViewport();

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
			await dataSetFragmentPage.editPage({layout});

			await dataSetFragmentPage.tableWrapper.click();

			await dataSetFragmentPage.changeDataSetButton.click();

			const selectionListContainer =
				dataSetFragmentPage.selectDataSetModalFrame.locator(
					'.fds-admin-item-selector'
				);

			await expect(selectionListContainer).toBeVisible();

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

			await dataSetFragmentPage.publishPage();

			await dataSetFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that the Taxonomy Vocabulary Data Set is available on the page', async () => {
			await dataSetFragmentPage.tableWrapper.waitFor({
				state: 'visible',
			});

			await expect(
				await dataSetFragmentPage.tableWrapper
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
