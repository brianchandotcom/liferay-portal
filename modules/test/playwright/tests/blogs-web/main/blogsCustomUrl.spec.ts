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
	'Auto-generates the friendly URL from the title by default',
	{
		tag: '@LPS-69240',
	},
	async ({blogsEditBlogEntryPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		await blogsEditBlogEntryPage.fillTitle('Blogs Entry Title');
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.expandPanel('Friendly URL');

		await expect(blogsEditBlogEntryPage.customUrlInput).toHaveValue(
			'blogs-entry-title'
		);
	}
);

test(
	'Can save a custom URL for a blog entry',
	{
		tag: '@LPS-69240',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = 'Blogs Entry Title';
		const customUrl = 'custom-url-here';

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.setCustomUrl(customUrl);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.expandPanel('Friendly URL');

		await expect(blogsEditBlogEntryPage.customUrlInput).toHaveValue(
			customUrl
		);
	}
);

test(
	'Cannot add a blog entry with a duplicate custom URL through the UI',
	{
		tag: '@LPS-69240',
	},
	async ({apiHelpers, blogsEditBlogEntryPage, site}) => {
		const title = 'Blogs Entry Title';

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: 'Blogs Entry Content',
			headline: title,
		});

		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		await blogsEditBlogEntryPage.editBlogEntry({
			content: 'Blogs Entry Content',
			publish: false,
			title,
		});

		await blogsEditBlogEntryPage.setCustomUrl('blogs-entry-title');

		await blogsEditBlogEntryPage.publishBlogEntryExpectError(
			'URL title is already in use'
		);
	}
);

test(
	'Normalizes a friendly URL with escape characters',
	{
		tag: '@LPS-136769',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = 'Blogs Entry Title';
		const content = 'Blogs Entry Content';

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent(content);

		await blogsEditBlogEntryPage.setCustomUrl('blog&entry');

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.expandPanel('Friendly URL');

		await expect(blogsEditBlogEntryPage.customUrlInput).toHaveValue(
			'blog-entry'
		);
	}
);
