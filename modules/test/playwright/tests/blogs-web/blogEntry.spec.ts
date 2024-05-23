/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {blogsPagesTest} from './fixtures/blogsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	blogsPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPD-11147': true,
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
	page,
	site,
}) => {
	const vocabularyName = getRandomString();
	const friendlyUrlCategories = ['category-1', 'category-2', 'category-3'];

	const {id: vocabularyId} =
		await apiHelpers.headlessAdminTaxonomy.createVocabulary({
			name: vocabularyName,
			siteId: site.id,
		});

	for (const categoryName of friendlyUrlCategories) {
		await apiHelpers.headlessAdminTaxonomy.createCategory({
			name: categoryName,
			vocabularyId,
		});
	}

	await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

	const title = getRandomString();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: getRandomString(),
		friendlyUrl: {categories: friendlyUrlCategories},
		publish: false,
		title,
	});

	await expect(
		page.getByText(`/-/blogs/${friendlyUrlCategories.join('/')}/`)
	).toBeVisible();
});
