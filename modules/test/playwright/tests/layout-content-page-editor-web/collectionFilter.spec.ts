/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {collectionsPagesTest} from '../../fixtures/CollectionsPageTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {wemSiteTest} from '../../fixtures/wemSiteTest';
import getRandomString from '../../utils/getRandomString';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
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

const CONTENT_NAMES = [
	'sample 01',
	'sample 02 - Dogs and Cats categories',
	'sample 03 - Dogs category',
];

export const test = mergeTests(
	apiHelpersTest,
	collectionsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	loginTest(),
	pageEditorPagesTest,
	wemSiteTest
);

const selectFilter = async (page, categories) => {
	await page.getByRole('button', {name: 'Select'}).click();

	for (const category of categories) {
		await page.getByLabel(category).check({trial: true});
		await page.getByLabel(category).check({timeout: 1000});
	}

	await page.getByRole('button', {name: 'Apply'}).click();

	await page.waitForTimeout(1000);
};

test('filters a web content collection by single and multiple categories', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const collectionFilterId = getRandomString();

	const collectionFilterDefinition = getFragmentDefinition(
		collectionFilterId,
		'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer'
	);

	// Create definition for a collection mapped to Samples collection

	const collectionName = 'Samples';

	const samplesClassPK = await collectionsPage.getCollectionClassPK(
		collectionName,
		wemSite.friendlyUrlPath
	);

	const samplesCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition(
			getRandomString(),
			'BASIC_COMPONENT-heading',
			{},
			FRAGMENT_FIELDS
		),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: samplesClassPK,
		id: getRandomString(),
		pageElements: [samplesCollection],
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

	await pageEditorPage.goToEditMode(layout, wemSite.friendlyUrlPath);

	await page.waitForTimeout(1000);

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await page.getByLabel('Select', {exact: true}).click();

	await page.waitForTimeout(1000);

	await page.getByLabel(collectionName).check();

	await page.getByLabel('Filter', {exact: true}).selectOption('category');

	await page.getByLabel('Select Source').click();

	await page
		.frameLocator('iframe[title="Select"]')
		.getByRole('link', {name: 'Animals'})
		.click();

	await page.waitForTimeout(1000);

	await page
		.frameLocator('iframe[title="Select"]')
		.getByRole('button', {name: 'Select This Level'})
		.click();

	await page.waitForTimeout(1000);

	// Check the option to show the label with the selected vocabulary

	await page.getByLabel('Show Label').check();

	await pageEditorPage.publishPage();

	// Go to view mode of the created page

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	for (const name of CONTENT_NAMES) {
		await expect(page.getByText(name)).toBeVisible();
	}

	await expect(page.getByText('Animals', {exact: true})).toBeVisible();

	// Select category filter: Cats

	await selectFilter(page, ['cats']);

	await expect(page.getByText(CONTENT_NAMES[0])).not.toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[1])).toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[2])).not.toBeVisible();

	// Select category filter: Cats and Dogs

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await selectFilter(page, ['dogs', 'cats']);

	await expect(page.getByText(CONTENT_NAMES[0])).not.toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[1])).toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[2])).toBeVisible();
});

test('filters a web content collection by single and multiple tags', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create two tags

	const tags = [];

	for (const tagName of ['Dogs', 'Cats']) {
		tags.push(
			await apiHelpers.headlessAdminTaxonomy.createTag({
				name: tagName,
				siteId: wemSite.id,
			})
		);
	}

	// Create two Web Contents with tags

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);
	const webContents = [
		{
			name: 'Web content with the tag Dogs',
			tags: [tags[0].name],
		},
		{
			name: 'Web content with the tags of Dogs and Cats',
			tags: [tags[0].name, tags[1].name],
		},
		{
			name: 'Web content without tags',
		},
	];

	for (const {name, tags} of webContents) {
		await addApprovedStructuredContent({
			apiHelpers,
			contentStructureId,
			siteId: wemSite.id,
			tags,
			title: name,
		});
	}

	// Create a dynamic collection with the previous Web Contents

	const collectionName = 'Animal Collection';

	await collectionsPage.goto(wemSite.friendlyUrlPath);

	const {classPK} = await collectionsPage.createWebContentDynamicCollection(
		collectionName,
		wemSite.friendlyUrlPath
	);

	// Create a page with Collection Display and Collection Filter fragments

	const collectionFilterId = getRandomString();

	const layout = await createPageWithCollectionAndFilterCollection({
		apiHelpers,
		classPK,
		collectionFilterId,
		siteId: wemSite.id,
	});

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.goToEditMode(layout, wemSite.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for tags

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel(collectionName).check();

	await page.getByLabel('Filter', {exact: true}).selectOption('tags');

	// Publish the page and go to the view mode

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

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
});

test('enables search field in dropdown list of Collection Filter', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const collectionFilterId = getRandomString();

	const collectionFilterDefinition = getFragmentDefinition(
		collectionFilterId,
		'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer'
	);

	// Create definition for a collection mapped to Samples collection

	const collectionName = 'Samples';

	const samplesClassPK = await collectionsPage.getCollectionClassPK(
		collectionName,
		wemSite.friendlyUrlPath
	);

	const samplesCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition(
			getRandomString(),
			'BASIC_COMPONENT-heading',
			{},
			FRAGMENT_FIELDS
		),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: samplesClassPK,
		id: getRandomString(),
		pageElements: [samplesCollection],
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

	await pageEditorPage.goToEditMode(layout, wemSite.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel(collectionName).check();

	await page.getByLabel('Filter', {exact: true}).selectOption('category');

	await page.getByLabel('Select Source').click();

	await page
		.frameLocator('iframe[title="Select"]')
		.getByRole('link', {name: 'Animals'})
		.click();

	await page.waitForTimeout(1000);

	await page
		.frameLocator('iframe[title="Select"]')
		.getByRole('button', {name: 'Select This Level'})
		.click();

	await page.waitForTimeout(1000);

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
});

test('filters the collection content by keywords using two filters', async ({
	apiHelpers,
	collectionsPage,
	page,
	pageEditorPage,
	wemSite,
}) => {

	// Create a definition for a Collection Filter

	const firstCollectionFilterId = getRandomString();
	const secondCollectionFilterId = getRandomString();

	const firstCollectionFilterDefinition = getFragmentDefinition(
		firstCollectionFilterId,
		'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer'
	);

	const secondFilterDefinition = getFragmentDefinition(
		secondCollectionFilterId,
		'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer'
	);

	// Create definition for a collection mapped to Samples collection

	const collectionName = 'Samples';

	const samplesClassPK = await collectionsPage.getCollectionClassPK(
		collectionName,
		wemSite.friendlyUrlPath
	);

	const samplesCollection = getCollectionItemDefinition(getRandomString(), [
		getFragmentDefinition(
			getRandomString(),
			'BASIC_COMPONENT-heading',
			{},
			FRAGMENT_FIELDS
		),
	]);

	const collectionDefinition = getCollectionDefinition({
		classPK: samplesClassPK,
		id: getRandomString(),
		pageElements: [samplesCollection],
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

	await pageEditorPage.goToEditMode(layout, wemSite.friendlyUrlPath);

	// Configure the first filter by keywords

	await pageEditorPage.selectFragment(firstCollectionFilterId);

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel(collectionName).check();

	await page.getByLabel('Filter', {exact: true}).selectOption('keywords');

	// Configure the second filter by keywords

	await pageEditorPage.selectFragment(secondCollectionFilterId);

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel(collectionName).check();

	await page.getByLabel('Filter', {exact: true}).selectOption('keywords');

	// Publish the page

	await pageEditorPage.publishPage();

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Filter by keywords

	const firstFilter = await page
		.getByPlaceholder('Search', {exact: true})
		.first();

	await firstFilter.fill('category categories');
	await firstFilter.press('Enter');

	await expect(page.getByText(CONTENT_NAMES[0])).not.toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[1])).toBeVisible();
	await expect(page.getByText(CONTENT_NAMES[2])).toBeVisible();

	const secondFilter = await page
		.getByPlaceholder('Search', {exact: true})
		.nth(1);

	await secondFilter.fill('sample');
	await secondFilter.press('Enter');

	CONTENT_NAMES.forEach(
		async (name) => await expect(page.getByText(name)).toBeVisible()
	);

	await page.goto(`/web${wemSite.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await firstFilter.fill('rabbit');
	await firstFilter.press('Enter');

	await expect(page.getByText('No Results Found')).toBeVisible();
});
