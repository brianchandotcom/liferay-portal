/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {displayPageTemplatesPagesTest} from '../../../fixtures/displayPageTemplatesPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {navigationMenusPagesTest} from './fixtures/navigationMenusPagesTest';

const test = mergeTests(
	apiHelpersTest,
	displayPageTemplatesPagesTest,
	isolatedSiteTest,
	loginTest(),
	navigationMenusPagesTest
);

test(
	'Assert DisplayPageTemplate remains visible after editing a NavigationMenuItem',
	{
		tag: '@LPD-69616',
	},
	async ({
		apiHelpers,
		displayPageTemplatesPage,
		navigationMenusPage,
		page,
		site,
	}) => {
		await displayPageTemplatesPage.goto(site.friendlyUrlPath);

		const displayPageTemplateName = getRandomString();

		await displayPageTemplatesPage.createTemplate({
			contentType: 'Blogs Entry',
			name: displayPageTemplateName,
		});

		await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);

		const blogTitle1 = getRandomString();

		const blogTitle2 = getRandomString();

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blogTitle1,
		});

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blogTitle2,
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		await navigationMenusPage.createNavigationMenu(getRandomString());

		await navigationMenusPage.addBlogItem(blogTitle1);

		await navigationMenusPage.changeBlogItem(blogTitle1, blogTitle2);

		await expect(
			page.locator('p.card-title', {hasText: blogTitle2})
		).toBeVisible();

		await expect(page.getByRole('paragraph').locator('svg')).toBeHidden();
	}
);

test(
	'Assert navigation menu items can be changed',
	{
		tag: '@LPD-56136',
	},
	async ({apiHelpers, navigationMenusPage, page, site}) => {
		const blogTitle1 = getRandomString();

		const blogTitle2 = getRandomString();

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blogTitle1,
		});

		await apiHelpers.headlessDelivery.postBlog(site.id, {
			headline: blogTitle2,
		});

		await navigationMenusPage.goto(site.friendlyUrlPath);

		await navigationMenusPage.createNavigationMenu(getRandomString());

		await navigationMenusPage.addBlogItem(blogTitle1);

		await navigationMenusPage.changeBlogItem(blogTitle1, blogTitle2);

		await expect(
			page.locator('p.card-title', {hasText: blogTitle2})
		).toBeVisible();

		const imageName1 = 'astronaut.png';

		const imageName2 = 'earth.png';

		await navigationMenusPage.addDocumentImageItem(imageName1);

		await navigationMenusPage.changeDocumentImageItem(
			imageName1,
			imageName2
		);

		await expect(
			page.locator('p.card-title', {hasText: imageName2})
		).toBeVisible();
	}
);
