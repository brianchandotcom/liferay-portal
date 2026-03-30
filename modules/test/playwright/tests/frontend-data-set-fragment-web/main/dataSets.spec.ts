/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataSetManagerApiHelpersTest} from '../../../fixtures/dataSetManagerApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import performLogin, {performLogout} from '../../../utils/performLogin';
import getBasicWebContentStructureId from '../../../utils/structured-content/getBasicWebContentStructureId';
import {dataSetFragmentPageTest} from './fixtures/dataSetFragmentPageTest';

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPS-164563': {enabled: true},
		'LPS-178052': {enabled: true},
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
	restEndpoint: '/v1.0/user-accounts',
	restSchema: 'UserAccount',
};

const structuredContentDataSetConfig = {
	erc: getRandomString(),
	label: getRandomString(),
	restApplication: '/headless-delivery/v1.0',
	restEndpoint: '/v1.0/sites/{siteId}/structured-contents',
	restSchema: 'StructuredContent',
};

test.afterEach(async ({apiHelpers, dataSetManagerApiHelpers}) => {
	for (const erc of dataSetERCs) {
		await dataSetManagerApiHelpers.deleteDataSet({
			erc,
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

		article = null;
	}
});

test(
	'Assign a data set to the "Data Set" fragment, change and delete assignment',
	{
		tag: '@LPS-172403',
	},
	async ({dataSetFragmentPage, dataSetManagerApiHelpers, layout, page}) => {
		const dataSetERC1 = getRandomString();
		const dataSetERC2 = getRandomString();
		const dataSetERC3 = getRandomString();
		const dataSetLabel1 = getRandomString();
		const dataSetLabel2 = getRandomString();
		const dataSetLabel3 = getRandomString();

		dataSetERCs.push(dataSetERC1);
		dataSetERCs.push(dataSetERC2);
		dataSetERCs.push(dataSetERC3);

		const dataSetInput1 =
			dataSetFragmentPage.selectDataSetModal.container.locator(
				`li:has-text("${dataSetLabel1}") input.custom-control-input`
			);
		const dataSetInput2 =
			dataSetFragmentPage.selectDataSetModal.container.locator(
				`li:has-text("${dataSetLabel2}") input.custom-control-input`
			);
		const dataSetInput3 =
			dataSetFragmentPage.selectDataSetModal.container.locator(
				`li:has-text("${dataSetLabel3}")`
			);

		await test.step('Create data sets', async () => {
			await dataSetManagerApiHelpers.createDataSet({
				erc: dataSetERC1,
				label: dataSetLabel1,
			});

			await dataSetManagerApiHelpers.createDataSet({
				erc: dataSetERC2,
				label: dataSetLabel2,
			});

			await dataSetManagerApiHelpers.createDataSet({
				erc: dataSetERC3,
				label: dataSetLabel3,
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

		await test.step('Open Data Set selection list', async () => {
			await dataSetFragmentPage.selectDataSetButton.click();

			await page.getByRole('dialog').isVisible();

			await page.getByRole('heading', {name: 'Select'}).isVisible();
		});

		await test.step('Check that data set without visualization modes are marked', async () => {
			const warningIcon = dataSetInput3.locator(
				'svg.lexicon-icon-exclamation-circle'
			);

			await expect(warningIcon).toBeVisible();
		});

		await test.step('Check that only one data set can be selected', async () => {
			await dataSetInput1.setChecked(true);

			await expect(dataSetInput1).toBeChecked();

			await dataSetInput2.setChecked(true);

			await expect(dataSetInput2).toBeChecked();

			await expect(dataSetInput1).not.toBeChecked();

			await dataSetFragmentPage.selectDataSetModal.cancelButton.click();
		});

		await test.step('Assign first data set to fragment', async () => {
			await dataSetFragmentPage.selectDataSetButton.click();

			await dataSetFragmentPage.selectDataSet(dataSetLabel1);
		});

		await test.step('Change assignment to second data set', async () => {
			await dataSetFragmentPage.changeDataSetButton.click();

			await expect(dataSetInput1).toBeChecked();

			await dataSetFragmentPage.selectDataSet(dataSetLabel2);
		});

		await test.step('Assert that the data set is available on the page', async () => {
			await expect(dataSetFragmentPage.table.container).toBeInViewport();

			expect(
				await dataSetFragmentPage.table.headRow
					.locator('th')
					.allInnerTexts()
			).toEqual(['ID', 'Field Name', 'Manage Columns Visibility']);
		});

		await test.step('Unassign data set', async () => {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: page.getByRole('menuitem', {
					name: 'Remove Data Set',
				}),
				trigger: page.getByRole('button', {
					name: 'View Data Set Options',
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
				await dataSetFragmentPage.table.container
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
		await expect(
			dataSetFragmentPage.selectDataSetModal.container.locator(
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

		await test.step('Create an Admin User Schema (User Accounts) Data Set and add fields', async () => {
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
				fieldName: 'familyName',
				label_i18n: {
					en_US: 'Family Name',
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

		await test.step('Configure Structured Content Schema Data Set fragment', async () => {
			await dataSetFragmentPage.configureDataSetFragment({
				dataSetLabel: structuredContentDataSetConfig.label,
				layout,
			});
		});

		await test.step('Assert that the Data Set is available on the page', async () => {
			await dataSetFragmentPage.table.container.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.table.container).toBeInViewport();

			expect(
				await dataSetFragmentPage.table.headRow
					.locator('th')
					.allInnerTexts()
			).toEqual(['Title', 'Description', 'Manage Columns Visibility']);

			expect(
				await dataSetFragmentPage.table.bodyRows
					.locator('td')
					.allInnerTexts()
			).toEqual(
				expect.arrayContaining([
					structuredContentTitle,
					structuredContentDescription,
				])
			);
		});

		await test.step('Confirm that we can change the Data Set and display the User Accounts Data Set', async () => {
			await dataSetFragmentPage.editPage({layout});

			await dataSetFragmentPage.table.container.click();

			await dataSetFragmentPage.changeDataSetButton.click();

			await dataSetFragmentPage.selectDataSetModal.container
				.locator('li')
				.filter({hasText: adminUserDataSetConfig.label})
				.locator('input')
				.click();

			await dataSetFragmentPage.selectDataSetModal.selectButton.click();

			await expect(dataSetFragmentPage.selectedDataSetInput).toHaveValue(
				adminUserDataSetConfig.label
			);

			await dataSetFragmentPage.publishPage();

			await dataSetFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that the User Schema (User Accounts) Data Set is available on the page', async () => {
			await dataSetFragmentPage.table.container.waitFor({
				state: 'visible',
			});

			await expect(dataSetFragmentPage.table.container).toBeInViewport();

			expect(
				await dataSetFragmentPage.table.headRow
					.locator('th')
					.allInnerTexts()
			).toEqual(['Family Name', 'Name', 'Manage Columns Visibility']);

			expect(
				await dataSetFragmentPage.table.bodyRows.count()
			).toBeGreaterThanOrEqual(1);

			expect(
				await dataSetFragmentPage.table.bodyRows
					.first()
					.locator('td')
					.allInnerTexts()
			).toHaveLength(3);
		});
	}
);

test('An unauthorized user accessing a page with a data set fragment', async ({
	dataSetFragmentPage,
	dataSetManagerApiHelpers,
	layout,
	page,
}) => {
	const dataSetERC = getRandomString();
	const dataSetLabel = getRandomString();

	dataSetERCs.push(dataSetERC);

	await test.step('Create data set', async () => {
		await dataSetManagerApiHelpers.createDataSet({
			erc: dataSetERC,
			label: dataSetLabel,
		});
	});

	await test.step('Create sample data for data sets', async () => {
		await dataSetManagerApiHelpers.createDataSetTableSection({
			dataSetERC,
			fieldName: 'fieldName',
			label_i18n: {en_US: 'Field Name'},
		});
	});

	await test.step('Configure Data Set fragment', async () => {
		await dataSetFragmentPage.configureDataSetFragment({
			dataSetLabel,
			layout,
		});
	});

	await test.step('Log out', async () => {
		await performLogout(page);

		await expect(page.getByRole('button', {name: 'Sign In'})).toBeVisible();
	});

	try {
		await test.step('Go to Data Set fragment page', async () => {
			await dataSetFragmentPage.goToPage({layout});

			await page
				.locator('.data-set-content-wrapper')
				.waitFor({state: 'visible'});
		});

		await test.step('Assert that no results are displayed', async () => {
			await expect(
				page
					.locator('.data-set-content-wrapper')
					.getByText('No Results Found')
			).toBeVisible();
		});
	}
	finally {
		await test.step('Log back in as admin', async () => {
			await performLogin(page, 'test');
		});
	}
});
