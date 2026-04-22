/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

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

async function setDisplayDate(page: Page, date: Date) {
	const mmddyyyy =
		String(date.getMonth() + 1).padStart(2, '0') +
		'/' +
		String(date.getDate()).padStart(2, '0') +
		'/' +
		date.getFullYear();

	await page
		.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_displayDate'
		)
		.fill(mmddyyyy);

	await page
		.locator(
			'#_com_liferay_blogs_web_portlet_BlogsAdminPortlet_displayDate'
		)
		.press('Tab');
}

test(
	'Can schedule a blog entry for a future date',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, blogsPage, page, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.expandPanel('Configuration');

		const future = new Date();

		future.setFullYear(future.getFullYear() + 5);

		await setDisplayDate(page, future);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);

		await expect(
			page
				.locator('.card')
				.filter({hasText: title})
				.getByText('Scheduled')
		).toBeVisible();
	}
);

test(
	'Can change a scheduled blog entry to the present and publish it',
	{
		tag: '@LPD-86549',
	},
	async ({blogsEditBlogEntryPage, blogsPage, page, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await blogsEditBlogEntryPage.fillTitle(title);
		await blogsEditBlogEntryPage.fillContent('Blogs Entry Content');

		await blogsEditBlogEntryPage.expandPanel('Configuration');

		const future = new Date();

		future.setFullYear(future.getFullYear() + 5);

		await setDisplayDate(page, future);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);
		await blogsPage.goToBlogEntryAction('Edit', title);

		await blogsEditBlogEntryPage.expandPanel('Configuration');

		const past = new Date();

		past.setMinutes(past.getMinutes() - 5);

		await setDisplayDate(page, past);

		await blogsEditBlogEntryPage.publishBlogEntry();

		await blogsPage.goto(site.friendlyUrlPath);

		await expect(
			page.locator('.card').filter({hasText: title})
		).toContainText('Approved');
	}
);
