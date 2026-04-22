/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {watchForDialog} from '../../../utils/watchForDialog';
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
	'Preserves bold and italic markup in the content field',
	{
		tag: '@LPD-86549',
	},
	async ({apiHelpers, blogsEditBlogEntryPage, blogsPage, site}) => {
		const title = getRandomString();

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: '<b>Bold Type</b> <i>Italics</i>',
			headline: title,
		});

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await expect(
			blogsEditBlogEntryPage.contentEditor.locator('b, strong')
		).toContainText('Bold Type');
		await expect(
			blogsEditBlogEntryPage.contentEditor.locator('i, em')
		).toContainText('Italics');
	}
);

test(
	'Preserves cite markup in the content field',
	{
		tag: '@LPD-86549',
	},
	async ({apiHelpers, blogsEditBlogEntryPage, blogsPage, site}) => {
		const title = getRandomString();

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			articleBody: '<cite>Cited Content</cite>',
			headline: title,
		});

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await expect(
			blogsEditBlogEntryPage.contentEditor.locator('cite')
		).toContainText('Cited Content');
	}
);

test(
	'Does not execute XSS stored in the content field',
	{
		tag: '@LPS-136764',
	},
	async ({apiHelpers, blogsPage, page, site}) => {
		const watcher = watchForDialog(page);

		try {
			const title = getRandomString();

			await apiHelpers.headlessDelivery.postBlog(site.id, {
				articleBody:
					'<p>Blogs Entry Content</p><script>alert(123);</script>',
				headline: title,
			});

			await blogsPage.goto(site.friendlyUrlPath);
			await blogsPage.goToBlogEntryAction('Edit', title);

			watcher.assertNoDialog();
		}
		finally {
			watcher.dispose();
		}
	}
);
