/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';
import addApprovedStructuredContent from '../../utils/structured-content/addApprovedStructuredContent';
import getBasicWebContentStructureId from '../../utils/structured-content/getBasicWebContentStructureId';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
import getAssetTypesDefinition from './utils/getAssetTypesDefinition';
import getCollectionDefinition from './utils/getCollectionDefinition';
import getCollectionItemDefinition from './utils/getCollectionItemDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	journalPagesTest,
	pageEditorPagesTest
);

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

const getCollectionClassPK = async (page) => {
	const url = await page.url();
	const urlParams = new URLSearchParams(url);

	return urlParams.get(
		'_com_liferay_asset_list_web_portlet_AssetListPortlet_assetListEntryId'
	);
};

const selectFilter = async (page, categories) => {
	await page.getByRole('button', {name: 'Select'}).click();

	for (const category of categories) {
		await page.getByLabel(category).check({trial: true});
		await page.getByLabel(category).check({timeout: 1000});
	}

	await page.getByRole('button', {name: 'Apply'}).click();
};

const createCollection = async (page, friendlyUrlPath) => {

	// Create a dynamic collection

	await page.goto(`/group${friendlyUrlPath}${PORTLET_URLS.collections}`);

	await page.getByRole('button', {name: 'New'}).first().click();

	await page.getByRole('menuitem', {name: 'Dynamic Collection'}).click();

	await page.getByPlaceholder('Title').fill('Animal Collection');

	await page.getByRole('button', {name: 'Save'}).click();

	await page.waitForTimeout(3000);

	// Configure the dynamic collection for Web Contents

	await page
		.getByLabel('Item Type')
		.selectOption({label: 'Web Content Article'});

	await page.waitForTimeout(3000);

	await page
		.locator('.asset-subtype:not(.hide)')
		.getByLabel('Item Subtype')
		.selectOption({label: 'Basic Web Content'});

	await page.getByRole('button', {name: 'Save'}).click();

	await page.waitForTimeout(3000);
};

const createPageWithCollectionAndFilterCollection = async ({
	apiHelpers,
	collectionFilterId,
	page,
	siteId,
}) => {
	const classPK = await getCollectionClassPK(page);

	const collectionFilterDefinition = getFragmentDefinition(
		collectionFilterId,
		'com.liferay.fragment.renderer.collection.filter.internal.CollectionFilterFragmentRenderer'
	);

	const collectionFragmentDefinition = getFragmentDefinition(
		getRandomString(),
		'BASIC_COMPONENT-heading',
		{},
		FRAGMENT_FIELDS
	);

	const collectionItemDefinition = getCollectionItemDefinition(
		getRandomString(),
		[collectionFragmentDefinition]
	);

	const collectionDefinition = getCollectionDefinition(
		getRandomString(),
		classPK,
		[collectionItemDefinition]
	);

	return await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			collectionFilterDefinition,
			collectionDefinition,
		]),
		siteId,
		title: getRandomString(),
	});
};

test('filters a web content collection by single and multiple categories', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a vocabulary

	const vocabulary = await apiHelpers.headlessAdminTaxonomy.createVocabulary({
		assetTypes: getAssetTypesDefinition(),
		name: 'Animals',
		siteId: site.id,
	});

	// Create two categories for the previous vocabulary

	const categories = [];

	for (const categoryName of ['Dogs', 'Cats']) {
		categories.push(
			await apiHelpers.headlessAdminTaxonomy.createCategory({
				name: categoryName,
				vocabularyId: vocabulary.id,
			})
		);
	}

	// Create two Web Contents with categories

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);
	const webContents = [
		{
			categoryIds: [categories[0].id],
			name: 'Web content with the category Dogs',
		},
		{
			categoryIds: [categories[0].id, categories[1].id],
			name: 'Web content with the categories of Dogs and Cats',
		},
		{
			name: 'Web content without categories',
		},
	];

	for (const {categoryIds, name} of webContents) {
		await addApprovedStructuredContent({
			apiHelpers,
			categoryIds,
			contentStructureId,
			siteId: site.id,
			title: name,
		});
	}

	// Create a dynamic collection with the previous Web Contents

	await createCollection(page, site.friendlyUrlPath);

	// Create a page with Collection Display and Collection Filter fragments

	const collectionFilterId = getRandomString();

	const layout = await createPageWithCollectionAndFilterCollection({
		apiHelpers,
		collectionFilterId,
		page,
		siteId: site.id,
	});

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel('Animal Collection').check();

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

	await pageEditorPage.publishPage();

	// Go to view mode of the created page

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	for (const {name} of webContents) {
		await expect(page.getByText(name)).toBeVisible();
	}

	// Select category filter: Cats

	await selectFilter(page, ['cats']);

	await expect(page.getByText(webContents[0].name)).not.toBeVisible();
	await expect(page.getByText(webContents[1].name)).toBeVisible();
	await expect(page.getByText(webContents[2].name)).not.toBeVisible();

	// Select category filter: Cats and Dogs

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	await selectFilter(page, ['dogs', 'cats']);

	await expect(page.getByText(webContents[0].name)).toBeVisible();
	await expect(page.getByText(webContents[1].name)).toBeVisible();
	await expect(page.getByText(webContents[2].name)).not.toBeVisible();
});

test('filters a web content collection by single and multiple tags', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create two tags

	const tags = [];

	for (const tagName of ['Dogs', 'Cats']) {
		tags.push(
			await apiHelpers.headlessAdminTaxonomy.createTag({
				name: tagName,
				siteId: site.id,
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
			siteId: site.id,
			tags,
			title: name,
		});
	}

	// Create a dynamic collection with the previous Web Contents

	await createCollection(page, site.friendlyUrlPath);

	// Create a page with Collection Display and Collection Filter fragments

	const collectionFilterId = getRandomString();

	const layout = await createPageWithCollectionAndFilterCollection({
		apiHelpers,
		collectionFilterId,
		page,
		siteId: site.id,
	});

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for tags

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel('Animal Collection').check();

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
});

test('enables search field in dropdown list of Collection Filter', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a vocabulary with two categories

	const vocabulary = await apiHelpers.headlessAdminTaxonomy.createVocabulary({
		assetTypes: getAssetTypesDefinition(),
		name: 'Animals',
		siteId: site.id,
	});

	const categories = [];

	for (const categoryName of ['Dogs', 'Cats']) {
		categories.push(
			await apiHelpers.headlessAdminTaxonomy.createCategory({
				name: categoryName,
				vocabularyId: vocabulary.id,
			})
		);
	}

	// Create a Web Content

	const contentStructureId = await getBasicWebContentStructureId(apiHelpers);
	await addApprovedStructuredContent({
		apiHelpers,
		contentStructureId,
		siteId: site.id,
		title: getRandomString(),
	});

	// Create a dynamic collection with the previous Web Content

	await createCollection(page, site.friendlyUrlPath);

	// Create a page with Collection Display and Collection Filter fragments

	const collectionFilterId = getRandomString();

	const layout = await createPageWithCollectionAndFilterCollection({
		apiHelpers,
		collectionFilterId,
		page,
		siteId: site.id,
	});

	// Go to edit mode of the created page and select the Collection Filter fragment

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	await pageEditorPage.selectFragment(collectionFilterId);

	// Set Filter configuration for categories

	await page.getByLabel('Select', {exact: true}).click();

	await page.getByLabel('Animal Collection').check();

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

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Check the categories that appear in the dropdown

	await page.getByRole('button', {name: 'Select'}).click();

	await expect(page.getByText('dogs')).toBeVisible();
	await expect(page.getByText('cats')).toBeVisible();

	await page.getByRole('textbox').fill('dogs');

	await expect(page.getByText('dogs')).toBeVisible();
	await expect(page.getByText('cats')).not.toBeVisible();
});
