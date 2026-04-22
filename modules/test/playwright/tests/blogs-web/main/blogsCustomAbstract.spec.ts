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
	'The custom abstract persists when typing in the content field',
	{
		tag: '@LPS-106613',
	},
	async ({blogsEditBlogEntryPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const abstract = 'Blogs Entry Abstract Description';

		await blogsEditBlogEntryPage.fillTitle('Blogs Entry Title');
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.setCustomAbstract(abstract);

		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content updated');

		await expect(blogsEditBlogEntryPage.abstractDescription).toHaveValue(
			abstract
		);
	}
);

test(
	'Can add special characters to the custom abstract',
	{
		tag: '@LPS-136761',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();
		const abstract = '~!@#$%^&*';

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.setCustomAbstract(abstract);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.expandPanel('Configuration');

		await expect(blogsEditBlogEntryPage.abstractDescription).toHaveValue(
			abstract
		);
	}
);

test(
	'Can edit the custom abstract',
	{
		tag: '@LPS-106613',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await blogsEditBlogEntryPage.editBlogEntry({
			content: 'Blogs Entry Content',
			publish: false,
			title,
		});

		await blogsEditBlogEntryPage.setCustomAbstract(
			'Blogs Entry Custom Abstract'
		);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content Edit');

		await blogsEditBlogEntryPage.setCustomAbstract(
			'Blogs Entry Custom AbstractEdit'
		);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.expandPanel('Configuration');

		await expect(blogsEditBlogEntryPage.abstractDescription).toHaveValue(
			'Blogs Entry Custom AbstractEdit'
		);

		await expect(blogsEditBlogEntryPage.contentEditor).toContainText(
			'Blogs Entry Content Edit'
		);
	}
);

test(
	'Cannot publish a blog entry when custom abstract is selected but empty',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		await blogsEditBlogEntryPage.fillTitle('Blogs Title');

		await blogsEditBlogEntryPage.expandPanel('Configuration');
		await blogsEditBlogEntryPage.customAbstractRadio.check();

		await blogsEditBlogEntryPage.publishBlogEntryExpectError(
			'Content field is required'
		);
	}
);

test(
	'Does not execute XSS stored in the custom abstract',
	{
		tag: '@LPS-136765',
	},
	async ({blogsEditBlogEntryPage, blogsPage, page, site}) => {
		const watcher = watchForDialog(page);

		try {
			await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

			const title = getRandomString();

			await blogsEditBlogEntryPage.editBlogEntry({
				content: 'Blogs Entry Content',
				publish: false,
				title,
			});

			await blogsEditBlogEntryPage.setCustomAbstract(
				'<script>alert(123);</script>'
			);

			await blogsEditBlogEntryPage.publishBlogEntry();

			await blogsPage.goto(site.friendlyUrlPath);

			watcher.assertNoDialog();
		}
		finally {
			watcher.dispose();
		}
	}
);
