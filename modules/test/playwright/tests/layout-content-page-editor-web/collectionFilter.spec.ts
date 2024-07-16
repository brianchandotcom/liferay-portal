/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {collectionsPagesTest} from '../../fixtures/collectionsPagesTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import {ANIMALS_COLLECTION_NAME} from '../../setup/wem-site/constants';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import getRandomString from '../../utils/getRandomString';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import createPageWithCollectionAndFilterCollection from './utils/createPageWithCollectionAndFilterCollection';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const FRAGMENT_FIELDS = [
	{
		id: 'element-text',
		value: {
			text: {
				mapping: {
					fieldKey: 'JournalArticle_title',
					itemReference: {
						contextSource: 'CollectionItem',
					},
				},
			},
		},
	},
];

const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

const testWithIsolatedSite = mergeTests(test, isolatedSiteTest);

const configureFilter = async (page: Page, option: 'category' | 'keywords') => {
	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel(ANIMALS_COLLECTION_NAME).check();

	await page.getByLabel('Filter', {exact: true}).selectOption(option);

	if (option === 'category') {
		await page.getByLabel('Select Source').click();

		await page
			.frameLocator('iframe[title="Select"]')
			.getByRole('link', {name: ANIMALS_COLLECTION_NAME})
			.click();

		await expect(
			page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Select This Level'})
		).toBeEnabled();

		await clickAndExpectToBeHidden({
			target: page.locator('.modal-dialog'),
			trigger: page
				.frameLocator('iframe[title="Select"]')
				.getByRole('button', {name: 'Select This Level'}),
		});

		await expect(page.getByLabel('Source', {exact: true})).toHaveValue(
			ANIMALS_COLLECTION_NAME
		);
	}
};

const selectFilter = async (page, categories) => {
	await page.getByRole('button', {name: 'Select'}).click();

	for (const category of categories) {
		await page.getByLabel(category).check({trial: true});
		await page.getByLabel(category).check({timeout: 1000});
	}

	await page.getByRole('button', {name: 'Apply'}).click();

	await page.waitForURL(/(.)filter_category(.)/);
};

test('Filters a web content collection by single and multiple categories', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const collectionFilterId = getRandomString();

	const collectionFilterDefinition = getFragmentDefinition({
		id: collectionFilterId,
		key: 'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer',
	});

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({
			fragmentFields: FRAGMENT_FIELDS,
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		}),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			collectionFilterDefinition,
			collectionDefinition,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await configureFilter(page, 'category');

	// Check the option to show the label with the selected vocabulary

	await page.getByLabel('Show Label').check();

	await pageEditorPage.publishPage();

	// Go to view mode of the created page

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Both should be visible initially

	await expect(
		page.getByText('Animal 01 - Dogs and Cats categories')
	).toBeVisible();
	await expect(page.getByText('Animal 02 - Dogs category')).toBeVisible();

	await expect(
		page.getByText(ANIMALS_COLLECTION_NAME, {exact: true})
	).toBeVisible();

	// Select category filter: Cats

	await selectFilter(page, ['cats']);

	await expect(
		page.getByText('Animal 01 - Dogs and Cats categories')
	).toBeVisible();

	await expect(page.getByText('Animal 02 - Dogs category')).not.toBeVisible();

	// Select category filter: Cats and Dogs

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await selectFilter(page, ['dogs', 'cats']);

	await expect(
		page.getByText('Animal 01 - Dogs and Cats categories')
	).toBeVisible();

	await expect(page.getByText('Animal 02 - Dogs category')).toBeVisible();

	await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
});

testWithIsolatedSite(
	'Filters a web content collection by single and multiple tags',
	async ({apiHelpers, collectionsPage, page, pageEditorPage, site}) => {

		// Create two tags

		const tags = [];

		for (const tagName of ['Dogs', 'Cats']) {
			tags.push(
				await apiHelpers.headlessAdminTaxonomy.postSiteKeyword({
					name: tagName,
					siteId: site.id,
				})
			);
		}

		// Create two Web Contents with tags

		const contentStructureId =
			await getBasicWebContentStructureId(apiHelpers);
		const webContents = [
			{
				name: 'Web content with the tag Dogs',
				tags: ['Dogs'],
			},
			{
				name: 'Web content with the tags of Dogs and Cats',
				tags: ['Dogs', 'Cats'],
			},
			{
				name: 'Web content without tags',
			},
		];

		for (const {name, tags} of webContents) {
			await addApprovedStructuredContent({
				apiHelpers,
				contentStructureId,
				siteId: site.id,
				tags,
				title: name,
			});
		}

		// Create a dynamic collection with the previous Web Contents

		const collectionName = 'Animal Collection';

		await collectionsPage.goto(site.friendlyUrlPath);

		const {classPK} =
			await collectionsPage.createWebContentDynamicCollection(
				collectionName,
				site.friendlyUrlPath
			);

		// Create a page with Collection Display and Collection Filter fragments

		const collectionFilterId = getRandomString();

		const layout = await createPageWithCollectionAndFilterCollection({
			apiHelpers,
			classPK,
			collectionFilterId,
			siteId: site.id,
		});

		// Go to edit mode of the created page and select the Collection Filter fragment

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await pageEditorPage.selectFragment(collectionFilterId);

		// Set Filter configuration for tags

		await page.getByLabel('Select', {exact: true}).click();

		await page.getByLabel(collectionName).check();

		await page.getByLabel('Filter', {exact: true}).selectOption('tags');

		// Publish the page and go to the view mode

		await pageEditorPage.publishPage();

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

		for (const {name} of webContents) {
			await expect(page.getByText(name)).toBeVisible();
		}

		// Select tag filter: Cats

		await page.getByLabel('', {exact: true}).click();

		await page.getByRole('option', {name: 'Cats'}).click();

		await expect(page.getByText(webContents[0].name)).not.toBeVisible();
		await expect(page.getByText(webContents[1].name)).toBeVisible();
		await expect(page.getByText(webContents[2].name)).not.toBeVisible();

		// Select tag filter: Cats and Dogs

		await page.getByLabel('', {exact: true}).click();

		await page.getByRole('option', {name: 'Dogs'}).click();

		await expect(page.getByText(webContents[0].name)).toBeVisible();
		await expect(page.getByText(webContents[1].name)).toBeVisible();
		await expect(page.getByText(webContents[2].name)).not.toBeVisible();
	}
);

test('Enables search field in dropdown list of Collection Filter', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const collectionFilterId = getRandomString();

	const collectionFilterDefinition = getFragmentDefinition({
		id: collectionFilterId,
		key: 'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer',
	});

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({
			fragmentFields: FRAGMENT_FIELDS,
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		}),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			collectionFilterDefinition,
			collectionDefinition,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await configureFilter(page, 'category');

	await page.getByLabel('Include Search Field').check();

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Check the categories that appear in the dropdown

	await page.getByRole('button', {name: 'Select'}).click();

	await expect(page.getByText('Dogs', {exact: true})).toBeVisible();
	await expect(page.getByText('Cats', {exact: true})).toBeVisible();

	await page.getByRole('textbox').fill('dogs');

	await expect(page.getByText('Dogs', {exact: true})).toBeVisible();
	await expect(page.getByText('Cats', {exact: true})).not.toBeVisible();

	await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
});

test('Filters the collection content by keywords using two filters', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const firstCollectionFilterId = getRandomString();
	const secondCollectionFilterId = getRandomString();

	const firstCollectionFilterDefinition = getFragmentDefinition({
		id: firstCollectionFilterId,
		key: 'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer',
	});

	const secondFilterDefinition = getFragmentDefinition({
		id: secondCollectionFilterId,
		key: 'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer',
	});

	// Create definition for a collection mapped to Animals collection

	const animalsClassPK = await collectionsPage.getCollectionClassPK(
		ANIMALS_COLLECTION_NAME,
		wemSite.friendlyUrlPath
	);

	const animalsCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition({
			fragmentFields: FRAGMENT_FIELDS,
			id: getRandomString(),
			key: 'BASIC_COMPONENT-heading',
		}),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: animalsClassPK,
		id: getRandomString(),
		pageElements: [animalsCollection],
	});

	// Create a content page and go to edit mode

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			firstCollectionFilterDefinition,
			secondFilterDefinition,
			collectionDefinition,
		]),
		siteId: wemSite.id,
		title: getRandomString(),
	});

	// Go to edit mode

	await pageEditorPage.goto(layout, wemSite.friendlyUrlPath);

	// Configure the first filter by keywords

	await pageEditorPage.selectFragment(firstCollectionFilterId);

	await configureFilter(page, 'keywords');
	await pageEditorPage.waitForChangesSaved();

	// Configure the second filter by keywords

	await pageEditorPage.selectFragment(secondCollectionFilterId);

	await configureFilter(page, 'keywords');
	await pageEditorPage.waitForChangesSaved();

	// Publish the page

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Filter by keywords

	const firstFilter = page.getByPlaceholder('Search', {exact: true}).first();

	await firstFilter.fill('category categories');
	await firstFilter.press('Enter');

	await expect(
		page.getByText('Animal 01 - Dogs and Cats categories')
	).toBeVisible();
	await expect(page.getByText('Animal 02 - Dogs category')).toBeVisible();

	const secondFilter = page.getByPlaceholder('Search', {exact: true}).nth(1);

	await secondFilter.fill('Animal');
	await secondFilter.press('Enter');

	await expect(
		page.getByText('Animal 01 - Dogs and Cats categories')
	).toBeVisible();
	await expect(page.getByText('Animal 02 - Dogs category')).toBeVisible();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await firstFilter.fill('rabbit');
	await firstFilter.press('Enter');

	await expect(page.getByText('No Results Found')).toBeVisible();

	await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.id);
});
