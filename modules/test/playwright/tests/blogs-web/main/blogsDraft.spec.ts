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
	'Can manually save a blog entry as draft',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();
		const subtitle = getRandomString();
		const content = getRandomString();

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillSubtitle(subtitle);
		await blogsEditBlogEntryPage.fillContent(content);

		await blogsEditBlogEntryPage.saveAsDraft();

		await blogsPage.goto(site.friendlyUrlPath);

		await blogsPage.assertEntryPresent(title);

		await blogsPage.goToBlogEntryAction('Edit', title);

		await expect(blogsEditBlogEntryPage.titleInput).toHaveValue(title);
		await expect(blogsEditBlogEntryPage.subtitleInput).toHaveValue(
			subtitle
		);
		await expect(blogsEditBlogEntryPage.contentEditor).toContainText(
			content
		);
	}
);

test(
	'Can publish a blog entry that was saved as draft',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, blogsPage, page, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();
		const content = getRandomString();

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent(content);

		await blogsEditBlogEntryPage.saveAsDraft();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.fillContent(content + ' ');

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);

		await expect(
			page.locator('.card').filter({hasText: title})
		).toContainText('Approved');
	}
);

test(
	'Can delete a blog entry that was saved as draft',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, blogsPage, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent(getRandomString());

		await blogsEditBlogEntryPage.saveAsDraft();

		await blogsPage.goto(site.friendlyUrlPath);

		await blogsPage.moveEntryToRecycleBin(title);

		await blogsPage.goto(site.friendlyUrlPath);

		await blogsPage.assertEntryPresent(title, false);
	}
);
