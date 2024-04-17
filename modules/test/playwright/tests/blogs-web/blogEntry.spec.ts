/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {blogsPagesTest} from './fixtures/blogsPagesTest';

const baseTest = mergeTests(isolatedSiteTest, blogsPagesTest, loginTest());

const blogEntryAutoSaved = mergeTests(baseTest);

blogEntryAutoSaved(
	'LPD-22497: Permission sets are differing depending on autosaving of a blog entry',
	async ({blogsEditBlogEntryPage, blogsPage, page, site}) => {
		await blogsEditBlogEntryPage.goto(site.friendlyUrlPath);

		const title = getRandomString();

		await blogsEditBlogEntryPage.editBlogEntry(getRandomString(), title);

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
	}
);
