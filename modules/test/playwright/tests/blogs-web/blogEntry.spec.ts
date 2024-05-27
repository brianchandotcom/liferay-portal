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
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
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

	await page.getByRole('button', {name: 'Publish'}).click();
	await waitForSuccessAlert(page);

	const response = await page.goto(`/web${site.friendlyUrlPath}/b/${title}`);

	await expect(response.url()).toContain(
		`/web${site.friendlyUrlPath}/b/${friendlyUrlCategories.join(
			'/'
		)}/${title}`
	);
});
