/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {blogsPagesTest} from './fixtures/blogsPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	blogsPagesTest,
	loginTest(),
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test(
	'Can search blog entries by content',
	{
		tag: '@LPD-86549',
	},
	async ({apiHelpers, blogsPage, site}) => {
		const title = 'Blogs Entry Title';

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: 'Unique Content',
			headline: title,
		});

		await blogsPage.goto(site.friendlyUrlPath);

		for (const searchTerm of ['Unique', 'Unique Content']) {
			await blogsPage.searchEntry(searchTerm);

			await blogsPage.assertEntryPresent(title);
		}
	}
);

test(
	'Can search blog entries by title',
	{
		tag: '@LPD-86549',
	},
	async ({apiHelpers, blogsPage, site}) => {
		const title = 'Blogs Entry Title';

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: 'Unique Content',
			headline: title,
		});

		await blogsPage.goto(site.friendlyUrlPath);

		for (const searchTerm of ['Blogs', 'Blogs Entry Title']) {
			await blogsPage.searchEntry(searchTerm);

			await blogsPage.assertEntryPresent(title);
		}
	}
);

test(
	'Can find a draft blog entry by search',
	{
		tag: '@LPS-80502',
	},
	async ({apiHelpers, blogsEditBlogEntryPage, blogsPage, page, site}) => {
		const title = 'Blogs Entry Title';

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: 'Blogs Entry Content',
			headline: title,
		});

		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);
		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');
		await blogsEditBlogEntryPage.saveAsDraft();

		await blogsPage.goto(site.friendlyUrlPath);

		await blogsPage.searchEntry(title);

		await expect(
			page.locator('.card').filter({hasText: title})
		).toHaveCount(2);
	}
);

test(
	'Can edit a blog entry from the admin search results',
	{
		tag: '@LPS-108029',
	},
	async ({apiHelpers, blogsEditBlogEntryPage, blogsPage, site}) => {
		const title = 'Blogs Entry Title';
		const editedTitle = 'Blogs Entry Title Edit';

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: 'Blogs Entry Content',
			headline: title,
		});

		await blogsPage.goto(site.friendlyUrlPath);

		await blogsPage.searchEntry(title);

		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.fillTitle(editedTitle);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content Edit');

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.assertEntryPresent(editedTitle);
	}
);
