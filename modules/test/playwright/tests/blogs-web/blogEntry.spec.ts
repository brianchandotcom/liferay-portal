/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {blogsPagesTest} from './fixtures/blogsPagesTest';
import {friendlyURLCategoriesSetup} from './utils/friendlyURLCategoriesSetup';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	blogsPagesTest,
	pageEditorPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-11147': true,
		'LPS-178052': true,
	})
);

test('LPD-22497: Permission sets are differing depending on autosaving of a blog entry', async ({
	blogsEditBlogEntryPage,
	blogsPage,
	page,
	site,
}) => {
	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		publish: false,
		title,
	});

	await expect(
		page.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_saveStatus'
		)
	).toContainText('Draft Saved at', {
		timeout: 40000,
	});

	await blogsPage.goto(site.friendlyUrlPath);

	await blogsPage.assertBlogEntryPermissions(
		[
			{enabled: true, locator: '#guest_ACTION_ADD_DISCUSSION'},
			{enabled: true, locator: '#guest_ACTION_VIEW'},
			{enabled: true, locator: '#site-member_ACTION_ADD_DISCUSSION'},
			{enabled: true, locator: '#site-member_ACTION_VIEW'},
		],
		title
	);
});

test('LPD-26752 Select categories for the custom friendly URL', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}) => {
	const vocabularyName = getRandomString();
	const friendlyUrlCategories = ['category-1', 'category-2', 'category-3'];

	await friendlyURLCategoriesSetup({
		apiHelpers,
		displayPageTemplatesPage,
		friendlyUrlCategories,
		page,
		pageEditorPage,
		site,
		vocabularyName,
	});

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		friendlyUrl: {categories: friendlyUrlCategories, vocabularyName},
		publish: false,
		title,
	});

	await expect(
		page.getByText(`/-/blogs/${friendlyUrlCategories.join('/')}/`)
	).toBeVisible();

	await blogsEditBlogEntryPage.publishBlogEntry();

	const response = await page.goto(`/web${site.friendlyUrlPath}/b/${title}`);

	await expect(response.url()).toContain(
		`/web${site.friendlyUrlPath}/b/${friendlyUrlCategories.join(
			'/'
		)}/${title}`
	);
});

test('LPD-24858 Categories with blank spaces in friendly URL', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}) => {
	const vocabularyName = getRandomString();
	const friendlyUrlCategories = ['category 1', 'category 2', 'category 3'];

	await friendlyURLCategoriesSetup({
		apiHelpers,
		displayPageTemplatesPage,
		friendlyUrlCategories,
		page,
		pageEditorPage,
		site,
		vocabularyName,
	});

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		friendlyUrl: {categories: friendlyUrlCategories, vocabularyName},
		publish: true,
		title,
	});

	const response = await page.goto(`/web${site.friendlyUrlPath}/b/${title}`);

	await expect(response.url()).toContain(
		`/web${site.friendlyUrlPath}/b/${friendlyUrlCategories
			.map((category) => encodeURIComponent(category))
			.join('/')}/${title}`
	);
});

test('LPD-26753 The URL changes when a category is modified', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	displayPageTemplatesPage,
	page,
	pageEditorPage,
	site,
}) => {
	const vocabularyName = getRandomString();
	const friendlyUrlCategories = ['category-1', 'category-2', 'category-3'];

	const {categories} = await friendlyURLCategoriesSetup({
		apiHelpers,
		displayPageTemplatesPage,
		friendlyUrlCategories,
		page,
		pageEditorPage,
		site,
		vocabularyName,
	});

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		friendlyUrl: {categories: friendlyUrlCategories, vocabularyName},
		publish: true,
		title,
	});

	const initialResponse = await page.goto(
		`/web${site.friendlyUrlPath}/b/${title}`
	);
	await expect(initialResponse.url()).toContain(
		`/web${site.friendlyUrlPath}/b/${friendlyUrlCategories.join(
			'/'
		)}/${title}`
	);

	friendlyUrlCategories[0] = `${friendlyUrlCategories[0]}-edited`;
	await apiHelpers.headlessAdminTaxonomy.patchCategory({
		id: categories[0].id,
		name: friendlyUrlCategories[0],
	});
	const editedResponse = await page.goto(initialResponse.url());
	await expect(editedResponse.url()).toContain(
		`/web${site.friendlyUrlPath}/b/${friendlyUrlCategories.join(
			'/'
		)}/${title}`
	);
});
